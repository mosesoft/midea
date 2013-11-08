package com.midea.iot.activities;

import com.midea.iot.R;


public interface AppConstant {
	
	public final static String PACKA_NAME = "com.midea.iot";
	
	public final static String MAIN_ACTIVITY_NAME = PACKA_NAME + "activites.MainActivity";
	public final static String SETTINGS_ACTIVITY_NAME = PACKA_NAME + "activites.SettingsActivity";

	public final static String ACTION_EXIT = "com.midea.iot.action.exit";
	final static String PASSWORD1 = "0245";
	final static String PASSWORD2 = "012345";
	final static String[][] label = {
		{"MWIP1", "MWIP2", "MWIP3","MWIP4","MWPORT"},
		{"OVIP1", "OVIP2", "OVIP3","OVIP4","OVPORT"},
		{"HOIP1", "HOIP2", "HOIP3","HOIP4","HOPORT"},
		{"HOBIP1", "HOBIP2", "HOBIP3","HOBIP4","HOBPORT"},
		{"MOIP1", "MOIP2", "MOIP3","MOIP4","MOPORT"},
		{"CLIP1", "CLIP2", "CLIP3","CLIP4","CLPORT"}
	};
	
	final static String[] time_label = {
		"MW_TIME",
		"OV_TIME",
		"HO_TIME",
		"HOB_TIME",
		"MO_TIME",
		"CL_TIME"
	};
	
	final static String[] mwo_label = {
		"MWO_TIME",
		"MWO_TEMP",
		"MWO_WEIGHT",
		"MWO_POWER"
	};
	final static String[] name = {
		"Microwave",
		"Oven",
		"Hood",
		"Hob",
		"Mini Oven",
		"Robot Clean"
	};
	
	final static String[] stateArray = {
		"Wait...",
		"Cooking...",
		"Pause",
		"Wait..."
	};
	
	final static String[] hoodstateArray = {
		"",
		"Working...",
		"Working...",
		"Working..."
	};
	final static int SCROLL_DURATION_SHORT = 300;
	final static int SCROLL_DURATION_LONG = 500;
	
	final static int SLIDE_INIT = 0;
	final static int SLIDING = 1;
	final static int SLIDE_RIGHT = 2;
	final static int NOT_OPERATE = 0;
	final static int HAVE_OPERATE = 1;
	
	final static int MICROWAVE = 0;
	final static int OVEN = 1;
	final static int HOOD = 2;
	final static int HOB = 3;
	final static int MINIOVEN = 4;
	final static int CLEAN = 5;
	
	final static int WAIT = 0;
	final static int RUN = 1;
	final static int PAUSE = 2;
	final static int FINISH = 3;
	
	final static int CONN_FAILED = 0;
	final static int CONN_NORMAL = 1;
	final static int CONN_SUCCESS = 2;
	
	final static int MODE_1 = 0;
	final static int MODE_2 = 1;
	final static int MODE_3 = 2;
	final static int MODE_4 = 3;
	final static int MODE_5 = 4;
	final static int MODE_6 = 5;
	final static int MODE_7 = 6;
	final static int MODE_8 = 7;
	
	final static int POWER_1 = 0;
	final static int POWER_2 = 1;
	final static int POWER_3 = 2;
	final static int POWER_4 = 3;
	final static int POWER_5 = 4;
	
	final static int COOKING_WAIT = 0;
	final static int COOKING_RUN = 1;
	final static int COOKING_PAUSE = 2;
	final static int COOKING_FINISH = 3;
	
	final static int HOOD_STOP = 0;
	final static int HOOD_F1 = 1;
	final static int HOOD_F2 = 2;
	final static int HOOD_F3 = 3;
	
	final static int CLEAN_STOP = 0;
	final static int CLEAN_CHARGING = 1;
	final static int CLEAN_WORKING = 2;
	final static int CLEAN_AUTO_CHARGING = 3;
	public static final String KEY_ID = "_id";
	public static final String KEY_1 = "mode";
	public static final String KEY_2 = "default_time";
	public static final String KEY_3 = "cur_time";
	public static final String KEY_4 = "min_time";
	public static final String KEY_5 = "max_time";
	public static final String KEY_6 = "default_temp";
	public static final String KEY_7 = "cur_temp";
	public static final String KEY_8 = "min_temp";
	public static final String KEY_9 = "max_temp";
	public static final String KEY_10 = "temp_step";
	public static final String KEY_11 = "appliance_id";
	final static String[][] default_data = {
		{Integer.toString(MODE_1), "539", "1","539","220","50","250","5"},
		{Integer.toString(MODE_2), "539", "1","539","220","50","250","5"},
		{Integer.toString(MODE_3), "539", "1","539","210","180","240","30"},
		{Integer.toString(MODE_4), "539", "1","539","210","180","240","30"},
		{Integer.toString(MODE_5), "539", "1","539","180","50","240","5"},
		{Integer.toString(MODE_6), "539", "1","539","210","180","240","30"},
		{Integer.toString(MODE_7), "539", "1","539","0","0","0","0"},
		{Integer.toString(MODE_8), "539", "1","539","60","60","120","5"}
	};
	
	final static String[][] minioven_default_data = {
		{Integer.toString(MODE_1), "1800", "1","5399","70","70","205","15"},
		{Integer.toString(MODE_2), "1800", "1","5399","70","70","190","15"},
		{Integer.toString(MODE_3), "1800", "1","5399","70","70","220","15"},
		{Integer.toString(MODE_4), "1800", "1","5399","70","70","220","15"},
		{Integer.toString(MODE_5), "1800", "1","5399","70","70","220","15"}
	};
	
	final static String[][] microwave_default_data = {
		//ģʽ                                   
		{Integer.toString(MODE_1), "0", "0","5695","0","0","0","0","4","0","4","0","0","0"},
		{Integer.toString(MODE_2), "0", "0","5695","0","0","0","0","0","0","0","0","0","0"},
		{Integer.toString(MODE_3), "0", "0","5695","150","150","240","10","0","0","0","0","0","0"},
		{Integer.toString(MODE_4), "0", "0","5695","0","0","0","0","0","0","0","0","0","0"},
		{Integer.toString(MODE_5), "0", "0","0","0","0","0","0","0","0","0","100","100","2000"}
	};
	final static int [] connFailedIcon = {
			R.drawable.mwoff,
			R.drawable.ovenoff,
			R.drawable.hoodoff,
			R.drawable.hoboff,
			R.drawable.miniovenoff,
			R.drawable.cleanoff
		};
		
	final static int [] connNormalIcon = {
			R.drawable.mwnor,
			R.drawable.ovennor,
			R.drawable.hoodnor,
			R.drawable.hobnor,
			R.drawable.miniovennor,
			R.drawable.cleannor
	};

	final static int [] connOKIcon = {
			R.drawable.mwsel,
			R.drawable.ovensel,
			R.drawable.hoodsel,
			R.drawable.hobsel,
			R.drawable.miniovensel,
			R.drawable.cleansel
	};
	
	final static int [] quick_nor = {
			R.drawable.mwo_nor,
			R.drawable.oven_nor,
			R.drawable.hood_nor,
			R.drawable.hob_nor,
			R.drawable.minioven_nor,
			R.drawable.clean_nor
		};
	final static int [] quick_sel = {
			R.drawable.mwo_sel,
			R.drawable.oven_sel,
			R.drawable.hood_sel,
			R.drawable.hob_sel,
			R.drawable.minioven_sel,
			R.drawable.clean_sel
		};
		
	final static int [] digital_mid= {
			R.drawable.digital00,
			R.drawable.digital01,
			R.drawable.digital02,
			R.drawable.digital03,
			R.drawable.digital04,
			R.drawable.digital05,
			R.drawable.digital06,
			R.drawable.digital07,
			R.drawable.digital08,
			R.drawable.digital09
		};
	final static int [] digital_large= {
			R.drawable.d_0,
			R.drawable.d_1,
			R.drawable.d_2,
			R.drawable.d_3,
			R.drawable.d_4,
			R.drawable.d_5,
			R.drawable.d_6,
			R.drawable.d_7,
			R.drawable.d_8,
			R.drawable.d_9
		};
	final static int [] digital_time_green = {
		R.drawable.time_green_0,
		R.drawable.time_green_1,
		R.drawable.time_green_2,
		R.drawable.time_green_3,
		R.drawable.time_green_4,
		R.drawable.time_green_5,
		R.drawable.time_green_6,
		R.drawable.time_green_7,
		R.drawable.time_green_8,
		R.drawable.time_green_9
	};
	final static int [] white_large= {
		R.drawable.white_0,
		R.drawable.white_1,
		R.drawable.white_2,
		R.drawable.white_3,
		R.drawable.white_4,
		R.drawable.white_5,
		R.drawable.white_6,
		R.drawable.white_7,
		R.drawable.white_8,
		R.drawable.white_9
	};
	final static int [] gray_large= {
		R.drawable.gray_0,
		R.drawable.gray_1,
		R.drawable.gray_2,
		R.drawable.gray_3,
		R.drawable.gray_4,
		R.drawable.gray_5,
		R.drawable.gray_6,
		R.drawable.gray_7,
		R.drawable.gray_8,
		R.drawable.gray_9
	};
	final static int [] mode_nor= {
			R.drawable.icon1_nor,
			R.drawable.icon2_nor,
			R.drawable.icon3_nor,
			R.drawable.icon4_nor,
			R.drawable.icon5_nor,
			R.drawable.icon6_nor,
			R.drawable.icon7_nor,
			R.drawable.icon8_nor
		};
	final static int [] mode_sel= {
			R.drawable.icon1_sel,
			R.drawable.icon2_sel,
			R.drawable.icon3_sel,
			R.drawable.icon4_sel,
			R.drawable.icon5_sel,
			R.drawable.icon6_sel,
			R.drawable.icon7_sel,
			R.drawable.icon8_sel
		};
	
	final static int [] mwo_mode_nor= {
		R.drawable.mwo_icon1_nor,
		R.drawable.mwo_icon2_nor,
		R.drawable.mwo_icon3_nor,
		R.drawable.mwo_icon4_nor,
		R.drawable.mwo_icon5_nor
	};
	final static int [] mwo_mode_sel= {
		R.drawable.mwo_icon1_sel,
		R.drawable.mwo_icon2_sel,
		R.drawable.mwo_icon3_sel,
		R.drawable.mwo_icon4_sel,
		R.drawable.mwo_icon5_sel
	};
	
	final static int [] minioven_mode_nor= {
		R.drawable.minioven_icon1_nor,
		R.drawable.minioven_icon2_nor,
		R.drawable.minioven_icon3_nor,
		R.drawable.minioven_icon4_nor,
		R.drawable.minioven_icon5_nor
	};
	final static int [] minioven_mode_sel= {
		R.drawable.minioven_icon1_sel,
		R.drawable.minioven_icon2_sel,
		R.drawable.minioven_icon3_sel,
		R.drawable.minioven_icon4_sel,
		R.drawable.minioven_icon5_sel
	};
	
	final static int [] minioven_mode_icon= {
		R.drawable.minioven_icon1,
		R.drawable.minioven_icon2,
		R.drawable.minioven_icon3,
		R.drawable.minioven_icon4,
		R.drawable.minioven_icon5
	};
	
	final static int [] mwo_mode_icon= {
		R.drawable.mwo_icon1,
		R.drawable.mwo_icon2,
		R.drawable.mwo_icon3,
		R.drawable.mwo_icon4,
		R.drawable.mwo_icon5
	};
	
	final static int [] mwo_power_sel= {
		R.drawable.power1_sel,
		R.drawable.power2_sel,
		R.drawable.power3_sel,
		R.drawable.power4_sel,
		R.drawable.power5_sel
	};
	
	final static int [] oven_mode_icon= {
			R.drawable.f1,
			R.drawable.f2,
			R.drawable.f3,
			R.drawable.f4,
			R.drawable.f5,
			R.drawable.f6,
			R.drawable.f7,
			R.drawable.f8
	};
	
	final static String [] oven_mode_name= {
			"Conventional",
			"Conventional+fan",
			"Double grilling",
			"Double grilling + fan",
			"Convection",
			"Radiant grilling",
			"Defrost",
			"Bottom heat"
		};
	
	final static String [] mwo_mode_name= {
		"Microwave",
		"Grill",
		"Convection",
		"Time Defrost(Solo)",
		"Weight Defrost(Solo)"
	};
	
	final static String [] minioven_mode_name= {
		"Bake",
		"Broil",
		"Toast",
		"Convection",
		"Rotisserie Grill"
	};
	
	final static int [] hobWhiteTimeNum = {
		R.drawable.hob_white_time_num_0,
		R.drawable.hob_white_time_num_1,
		R.drawable.hob_white_time_num_2,
		R.drawable.hob_white_time_num_3,
		R.drawable.hob_white_time_num_4,
		R.drawable.hob_white_time_num_5,
		R.drawable.hob_white_time_num_6,
		R.drawable.hob_white_time_num_7,
		R.drawable.hob_white_time_num_8,
		R.drawable.hob_white_time_num_9,
		R.drawable.hob_white_time_sign
	};
	
	final static int [] hobGreenTimeNum = {
		R.drawable.hob_green_time_num_0,
		R.drawable.hob_green_time_num_1,
		R.drawable.hob_green_time_num_2,
		R.drawable.hob_green_time_num_3,
		R.drawable.hob_green_time_num_4,
		R.drawable.hob_green_time_num_5,
		R.drawable.hob_green_time_num_6,
		R.drawable.hob_green_time_num_7,
		R.drawable.hob_green_time_num_8,
		R.drawable.hob_green_time_num_9,
		R.drawable.hob_green_time_sign
	};
	
	final static int [] hobGrayTimeNum = {
		R.drawable.hob_gray_time_num_0,
		R.drawable.hob_gray_time_num_0,
		R.drawable.hob_gray_time_num_0,
		R.drawable.hob_gray_time_num_0,
		R.drawable.hob_gray_time_num_0,
		R.drawable.hob_gray_time_num_0,
		R.drawable.hob_gray_time_num_0,
		R.drawable.hob_gray_time_num_0,
		R.drawable.hob_gray_time_num_0,
		R.drawable.hob_gray_time_num_0,
		R.drawable.hob_gray_time_sign
	};
	
	final static int [] hobWhitePowerNum = {
		R.drawable.hob_white_power_num_0,
		R.drawable.hob_white_power_num_1,
		R.drawable.hob_white_power_num_2,
		R.drawable.hob_white_power_num_3,
		R.drawable.hob_white_power_num_4,
		R.drawable.hob_white_power_num_5,
		R.drawable.hob_white_power_num_6,
		R.drawable.hob_white_power_num_7,
		R.drawable.hob_white_power_num_8,
		R.drawable.hob_white_power_num_9
	};
	final static int [] hobGrayPowerNum = {
		R.drawable.hob_gray_power_num_0,
		R.drawable.hob_gray_power_num_0,
		R.drawable.hob_gray_power_num_0,
		R.drawable.hob_gray_power_num_0,
		R.drawable.hob_gray_power_num_0,
		R.drawable.hob_gray_power_num_0,
		R.drawable.hob_gray_power_num_0,
		R.drawable.hob_gray_power_num_0,
		R.drawable.hob_gray_power_num_0,
		R.drawable.hob_gray_power_num_0
	};
	final static int [] hobSelGrayPowerNum = {
		R.drawable.hob_sel_gray_power_num_0,
		R.drawable.hob_sel_gray_power_num_1,
		R.drawable.hob_sel_gray_power_num_2,
		R.drawable.hob_sel_gray_power_num_3,
		R.drawable.hob_sel_gray_power_num_4,
		R.drawable.hob_sel_gray_power_num_5,
		R.drawable.hob_sel_gray_power_num_6,
		R.drawable.hob_sel_gray_power_num_7,
		R.drawable.hob_sel_gray_power_num_8,
		R.drawable.hob_sel_gray_power_num_9
	};
	final static int [] hobWhitePowerImg = {
		R.drawable.hob_white_power_img_0,
		R.drawable.hob_white_power_img_1,
		R.drawable.hob_white_power_img_2,
		R.drawable.hob_white_power_img_3,
		R.drawable.hob_white_power_img_4,
		R.drawable.hob_white_power_img_5,
		R.drawable.hob_white_power_img_6,
		R.drawable.hob_white_power_img_7,
		R.drawable.hob_white_power_img_8,
		R.drawable.hob_white_power_img_9
	};
	final static int [] hobGrayPowerImg = {
		R.drawable.hob_gray_power_img_0,
		R.drawable.hob_gray_power_img_0,
		R.drawable.hob_gray_power_img_0,
		R.drawable.hob_gray_power_img_0,
		R.drawable.hob_gray_power_img_0,
		R.drawable.hob_gray_power_img_0,
		R.drawable.hob_gray_power_img_0,
		R.drawable.hob_gray_power_img_0,
		R.drawable.hob_gray_power_img_0,
		R.drawable.hob_gray_power_img_0
	};
	
	final static int [] hobSelGrayPowerImg = {
		R.drawable.hob_sel_gray_power_img_0,
		R.drawable.hob_sel_gray_power_img_1,
		R.drawable.hob_sel_gray_power_img_2,
		R.drawable.hob_sel_gray_power_img_3,
		R.drawable.hob_sel_gray_power_img_4,
		R.drawable.hob_sel_gray_power_img_5,
		R.drawable.hob_sel_gray_power_img_6,
		R.drawable.hob_sel_gray_power_img_7,
		R.drawable.hob_sel_gray_power_img_8,
		R.drawable.hob_sel_gray_power_img_9
	};
	final static int TIMEOUT_TIME = 2000;
	
	final static Float TOTAL_DEGREE = 360F;
	
	final static int UPDATE_TIME = 100;
	final static Float TIME_UNIT = 1000F;
	final static Float TOTAL_SECONDS = 60F;
	final static Float MAX_UPDATE_NUM = 60F*TIME_UNIT/UPDATE_TIME;
	final static Float STEP_PROGRESS = (TOTAL_DEGREE/TOTAL_SECONDS) * (UPDATE_TIME/TIME_UNIT);
	
	public static final String ACTION_DEVICE_REPLY = "midea.iot.action.DEVICE_REPLY";
	public static final String ACTION_DEVICE_UPDATE = "midea.iot.action.DEVICE_UPDATE";
	public static final String ACTION_DEVICE_TIMEOUT = "midea.iot.action.DEVICE_TIMEOUT";
	
	
	final static int CLEAN_CHCAGING_ANIMATE_TIME = 100;
	
	final static int HOB_START_TIME = 1000;
	
	final static int HOB_MODE_A = 0;
	final static int HOB_MODE_B = 1;
	
	final static int NUM_WHITE = 0;
	final static int NUM_GREEN = 1;
	final static int NUM_GRAY = 2;
}

