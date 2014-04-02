
public class Motors {
	
	//Note: Velocities are in the array in the order LF, RF, LR, RR
	//this assumes the front of the couch is the way the passengers are facing
	// _______________________________________
	// |LF								   RF |
	// |									  |
	// |									  |
	// |									  |
	// |LR								   RR |
	// |______________________________________|

	private DeviceManager devices;
	private int mC;
	
	private boolean eStop;
	private boolean idiotStop;
	private boolean runMode;
	private boolean motorConnection;
	private int malPackets;
	private String[] faults;
	
	private static final int NUM_ATTEMPTS = 10;
	private static final int SCALE_FACTOR = 32767;
	
	private static final int MOTOR_VID = 1240;
	private static final int MOTOR_PID = 86;

	
	//Out Commands  Host --> Controller
	private static final byte H_SET_SPEED = 'A';
	private static final byte H_SET_SETTING = 'B';
	private static final byte H_READ_SETTING = 'C';
	private static final byte H_READ_STATUS = 'D';
	private static final byte H_RESET_FAULT = 'E';
	private static final byte H_READ_CHARGE_STATE = 'F';
	private static final byte H_READ_CHARGE_CHAR = 'G';
	private static final byte H_READ_FAULT_STATE = 'H';
	private static final byte H_READ_SYS_STATE = 'I';
	
	//In Commands Controller --> Host
	private static final byte C_ACK = 'A';
	private static final byte C_MAL = 'B';
	private static final byte C_SETTING = 'C';
	private static final byte C_FAULT = 'D';
	private static final byte C_STATUS = 'E';
	private static final byte C_CANNOT_RESPOND = 'F';
	private static final byte C_CHARGE_STATUS = 'G';
	private static final byte C_CHARGE_CHAR = 'H';
	private static final byte C_SYSTEM_STATE = 'I';
	
	//Controller responses
	public static final int OK = 0;
	public static final int MAL = 1;
	public static final int NO_RESP = 2;
	public static final int DATA_ERR = 3;
	public static final int NO_MCS = 4;

	
	//Fault Codes
	public static final byte CONT_TEMP = 'B';
	public static final byte MOTOR_TEMP = 'D';
	public static final byte MOTOR_CURRENT = 'F';
	public static final byte BATTERY = 'I';
	public static final byte FIRMWARE = 'J';
	
	private static final String[] ALL_OK = {"OK", "OK", "OK", "OK"};



	//consider having the motors access the CONFIG file themselves esp if there is more config stuff to come
	public Motors(DeviceManager deviceManager){
		malPackets = 0;
		devices = deviceManager;
 		mC = devices.addDevice(MOTOR_VID, MOTOR_PID, "Motor Controllers");
		faults = ALL_OK;
		//checkFaults();
		//runMode = true;
		//idiotStop = false;
		//eStop = false;
		if(mC != -1){
					
		//TODO
		   //checkStatus
		   checkSysState();
		} else {
			String[] noCom = {"NO COMS", "NO COMS", "NO COMS", "NO COMS"};
			faults = noCom;
		}
	}
	
	//Getters
	public String[] getFaultStrings(){
		return faults;
	}
	
	public boolean isIdiotStop(){
		return idiotStop;
	}
	
	public boolean isEStop(){
		return eStop;
	}
	
	public boolean motorConnection(){
		return motorConnection;
	}
	
	public String getMode(){
		if(runMode){
			return "Running";
		} else {
			return "Charging";
		}
	}

	public int update(double[] vel){
		if(mC == -1){
			String[] comLost = {"NO COMS", "NO COMS", "NO COMS", "NO COMS"};
			faults = comLost;
			return 0;
		}
		checkSysState();
		
		if(runMode && !(idiotStop || eStop)){
			byte[] data;
			byte[] response = new byte[64];
	
			data = packageVelData(vel);
			response = devices.sendData(data, mC);
			return checkResponse(response, data[0]);
		}
		return DATA_ERR;
	}
	
	public int reset(){
		if(mC != -1){
			byte[] data = new byte[64];
			byte[] response = new byte[64];
			data[0] = H_RESET_FAULT;
			response = devices.sendData(data, mC);
			return checkResponse(response, data[0]);
		} else {
			return NO_MCS;
		}
	}
	
	private void checkMotorConnection(){
	//TODO
		motorConnection = true;
	}
	
	private void checkSysState(){
		byte[] data = new byte[64];
		byte[] response = new byte[64];
		data[0] = H_READ_SYS_STATE;
		response = devices.sendData(data, mC);
		if(response[0] == C_CANNOT_RESPOND){
            		eStop = true;
		} else if(response[0] != C_SYSTEM_STATE){
			System.out.println("DATA ERROR ON SYS STATE CHECK");
			System.out.println(response[0]);
		} else {
			if ((response[1] & 1) != 0){
				runMode = false;
			} else {
				runMode = true;
			}
			if ((response[1] & 2) != 0){
				eStop = true;
			} else {
				eStop = false;
			}
			if ((response[1] & 4) != 0){
				idiotStop = true;
			} else {
				idiotStop = false;
			}
			if ((response[1] & 8) != 0){
				checkFaults();
			}
		}
	}
	
	private void checkFaults(){
		byte[] data = new byte[64];
		byte[] response = new byte[64];
		data[0] = H_READ_FAULT_STATE;
		response = devices.sendData(data, mC);
		if(checkResponse(response, C_FAULT) != Motors.OK){
			System.out.println("Motors.checkFaults() DATA ERROR ON FAULT STATE CHECK");
		} else {
			System.out.println("Fault Code " + response[1] + "Source " + response[2] + "Byte 3" + response[3]);
			String fault = faultToString(response[1]);
			if (response[2] == 4){
				for(int i = 0; i < 4; i++){
					faults[i] = fault;
				}
			} else {
				faults[response[2]] = fault;
			}
		}
	}

	private String faultToString(int code){
           if(code == FIRMWARE){
		   return "Firmware";
	   } else if (code == BATTERY){
		   return "Bat Flat";
	   } else if (code == MOTOR_CURRENT){
		  return "Motor Curr";
	   } else if (code == MOTOR_TEMP){
		  return "Motor Temp";
	   } else if (code == CONT_TEMP){
		  return "Cont Temp";
	   } else {
		   return "Unknown";
	   }	   
	}
	
	private int checkResponse(byte[] response, byte expected){
		if (response[0] == C_ACK && response[1] == expected){
			return OK;
		} else if (response[0] == C_FAULT && expected == C_FAULT){
			return OK;
		} else if (response[0] == C_CANNOT_RESPOND){
			eStop = true;
			return OK;
		}
		System.out.print("MC resp unexpected: (Motors.checkResp()) ");
		if (response[0] == C_ACK) {
			System.out.println("Wrong ack");
			return DATA_ERR;
		} else if (response[0] == C_MAL){
			System.out.println("Mal");
			malPackets++;
			return MAL;
		} else if (response[0] == C_CANNOT_RESPOND){
			System.out.println("No resp");
			return NO_RESP;
		} else {
			System.out.println("Data error " + response[0]);
			return DATA_ERR;
		}
	}
		
	private byte[] packageVelData(double[] vel){
		byte[] num = new byte[2];
		byte[] data = new byte[64];

		
		for(int i = 0; i < 4; i++){
			vel[i] = vel[i] * SCALE_FACTOR;
		}
		
		data[0] = H_SET_SPEED;
		int j = 1;
		for(int i = 0; i < 4; i++){
			num = convert((int)vel[i]);
			data[j] = num[0];
			j++;
			data[j] = num[1];
			j++;
		}
		return data;
	}
	
	private byte[] convert(int num){
		byte[] result = new byte[2];
		
		result[1] = (byte) (num & 0xFF);
		result[0] = (byte) ((num >> 8) & 0xFF);
		
		return result;
	}
}
