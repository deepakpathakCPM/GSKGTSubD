package com.example.deepakp.gskgtsubd.xmlHandler;


import android.util.Log;

import com.example.deepakp.gskgtsubd.gettersetter.BrandMasterGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.CityMasterGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.FailureGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.JCPMasterGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.LoginGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.MappingWindowChecklistGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.NonWorkingReasonGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.PosmMappingGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.PosmMasterGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.StateMasterGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.StoreTypeMasterGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.TownMasterGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.WindowChecklistAnswerGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.WindowChecklistGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.WindowMappingGetterSetter;
import com.example.deepakp.gskgtsubd.gettersetter.WindowMasterGetterSetter;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


public class XMLHandlers {
    // FAILURE XML HANDLER
    public static FailureGetterSetter failureXMLHandler(XmlPullParser xpp, int eventType) {
        FailureGetterSetter failureGetterSetter = new FailureGetterSetter();
        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("STATUS")) {
                        failureGetterSetter.setStatus(xpp.nextText());
                    }
                    if (xpp.getName().equals("ERRORMSG")) {
                        failureGetterSetter.setErrorMsg(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return failureGetterSetter;
    }

    public static NonWorkingReasonGetterSetter nonWorkinReasonXML(XmlPullParser xpp,
                                                                  int eventType) {
        NonWorkingReasonGetterSetter nonworking = new NonWorkingReasonGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("META_DATA")) {
                        nonworking.setNonworking_table(xpp.nextText());
                    }
                    if (xpp.getName().equals("REASON_CD")) {
                        nonworking.setReason_cd(xpp.nextText());
                    }
                    if (xpp.getName().equals("REASON")) {
                        nonworking.setReason(xpp.nextText());
                    }
                    if (xpp.getName().equals("ENTRY_ALLOW")) {
                        nonworking.setEntry_allow(xpp.nextText());
                    }

                    if (xpp.getName().equals("IMAGE_ALLOW")) {
                        nonworking.setIMAGE_ALLOW(xpp.nextText());
                    }



                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return nonworking;
    }

    // LOGIN XML HANDLER
    public static LoginGetterSetter loginXMLHandler(XmlPullParser xpp, int eventType) {
        LoginGetterSetter lgs = new LoginGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("SUCCESS")) {
                        lgs.setResult(xpp.nextText());
                    }
                    if (xpp.getName().equals("APP_VERSION")) {
                        lgs.setVERSION(xpp.nextText());
                    }
                    if (xpp.getName().equals("APP_PATH")) {
                        lgs.setPATH(xpp.nextText());
                    }
                    if (xpp.getName().equals("CURRENTDATE")) {
                        lgs.setDATE(xpp.nextText());
                    }

                    if (xpp.getName().equals("RIGHTNAME")) {
                        lgs.setRIGHTNAME(xpp.nextText());
                    }
                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lgs;
    }

    //  XML HANDLER FOR CITY MASTER
    public static CityMasterGetterSetter CityMasterXMLHandler(XmlPullParser xpp, int eventType) {
        CityMasterGetterSetter jcpGetterSetter = new CityMasterGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("META_DATA")) {
                        jcpGetterSetter.setTable_CityMaster(xpp.nextText());
                    }

                    if (xpp.getName().equals("CITY_CD")) {
                        jcpGetterSetter.setCITY_CD(xpp.nextText());
                    }
                    if (xpp.getName().equals("CITY")) {
                        jcpGetterSetter.setCITY(xpp.nextText());
                    }
                    if (xpp.getName().equals("STATE_CD")) {
                        jcpGetterSetter.setSTATE_CD(xpp.nextText());
                    }
                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jcpGetterSetter;
    }

    //  XML HANDLER FOR CITY MASTER
    public static TownMasterGetterSetter TownMasterXMLHandler(XmlPullParser xpp, int eventType) {
        TownMasterGetterSetter jcpGetterSetter = new TownMasterGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("META_DATA")) {
                        jcpGetterSetter.setTable_TOWN_MASTER(xpp.nextText());
                    }

                    if (xpp.getName().equals("TOWN_CD")) {
                        jcpGetterSetter.setTOWN_CD(xpp.nextText());
                    }
                    if (xpp.getName().equals("TOWN")) {
                        jcpGetterSetter.setTOWN(xpp.nextText());
                    }
                    if (xpp.getName().equals("CITY_CD")) {
                        jcpGetterSetter.setCITY_CD(xpp.nextText());
                    }
                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jcpGetterSetter;
    }


    //  XML HANDLER FOR CITY MASTER
    public static PosmMappingGetterSetter POSMMAPPINGXMLHandler(XmlPullParser xpp, int eventType) {
        PosmMappingGetterSetter jcpGetterSetter = new PosmMappingGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("META_DATA")) {
                        jcpGetterSetter.setTable_POSMMAPPING(xpp.nextText());
                    }
                    if (xpp.getName().equals("STATE_CD")) {
                        jcpGetterSetter.setSTATE_CD(xpp.nextText());
                    }

                    if (xpp.getName().equals("STORETYPE_CD")) {
                        jcpGetterSetter.setSTORETYPE_CD(xpp.nextText());
                    }
                    if (xpp.getName().equals("BRAND_CD")) {
                        jcpGetterSetter.setBRAND_CD(xpp.nextText());
                    }
                    if (xpp.getName().equals("POSM_CD")) {
                        jcpGetterSetter.setPOSM_CD(xpp.nextText());
                    }
                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jcpGetterSetter;
    }

    //  XML HANDLER FOR JOURNEY_PLAN
    public static JCPMasterGetterSetter JCPMasterXMLHandler(XmlPullParser xpp, int eventType) {
        JCPMasterGetterSetter jcpGetterSetter = new JCPMasterGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("META_DATA")) {
                        jcpGetterSetter.setTable_JOURNEY_PLAN(xpp.nextText());
                    }


                    if (xpp.getName().equals("STORE_CD")) {
                        jcpGetterSetter.setSTORE_CD(xpp.nextText());
                    }
                    if (xpp.getName().equals("EMP_CD")) {
                        jcpGetterSetter.setEMP_CD(xpp.nextText());
                    }
                    if (xpp.getName().equals("VISIT_DATE")) {
                        jcpGetterSetter.setVISIT_DATE(xpp.nextText());
                    }
                    if (xpp.getName().equals("KEYACCOUNT")) {
                        jcpGetterSetter.setKEYACCOUNT(xpp.nextText());
                    }
                    if (xpp.getName().equals("STORENAME")) {
                        jcpGetterSetter.setSTORENAME(xpp.nextText());
                    }
                    if (xpp.getName().equals("CITY")) {
                        jcpGetterSetter.setCITY(xpp.nextText());
                    }
                    if (xpp.getName().equals("STORETYPE")) {
                        jcpGetterSetter.setSTORETYPE(xpp.nextText());
                    }
                    if (xpp.getName().equals("STATE_CD")) {
                        jcpGetterSetter.setSTATE_CD(xpp.nextText());
                    }
                    if (xpp.getName().equals("STORETYPE_CD")) {
                        jcpGetterSetter.setSTORETYPE_CD(xpp.nextText());
                    }
                    if (xpp.getName().equals("UPLOAD_STATUS")) {
                        jcpGetterSetter.setUPLOAD_STATUS(xpp.nextText());
                    }
                    if (xpp.getName().equals("CHECKOUT_STATUS")) {
                        jcpGetterSetter.setCHECKOUT_STATUS(xpp.nextText());
                    }
                    if (xpp.getName().equals("GEO_TAG")) {
                        jcpGetterSetter.setGEO_TAG(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jcpGetterSetter;
    }

    //  XML HANDLER FOR BRAND MASTER
    public static BrandMasterGetterSetter brandMasterXmlHandler(XmlPullParser xpp, int eventType) {
        BrandMasterGetterSetter brandMasterGetterSetter = new BrandMasterGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("META_DATA")) {
                        brandMasterGetterSetter.setTable_BrandMaster(xpp.nextText());
                    }

                    if (xpp.getName().equals("BRAND_CD")) {
                        brandMasterGetterSetter.setBRAND_CD(xpp.nextText());
                    }
                    if (xpp.getName().equals("BRAND")) {
                        brandMasterGetterSetter.setBRAND(xpp.nextText());
                    }
                    if (xpp.getName().equals("CATEGORY_CD")) {
                        brandMasterGetterSetter.setCATEGORY_CD(xpp.nextText());
                    }
                    if (xpp.getName().equals("COMPANY_CD")) {
                        brandMasterGetterSetter.setCOMPANY_CD(xpp.nextText());
                    }
                    if (xpp.getName().equals("BRAND_SEQUENCE")) {
                        brandMasterGetterSetter.setBRAND_SEQUENCE(xpp.nextText());
                    }
                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            Log.e("error in brandMasterXmlHandler", e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.e("error in brandMasterXmlHandler", e.toString());
            e.printStackTrace();
        }
        return brandMasterGetterSetter;
    }

    //  XML HANDLER FOR POSM MASTER
    public static PosmMasterGetterSetter posmMasterXmlHandler(XmlPullParser xpp, int eventType) {
        PosmMasterGetterSetter posmMasterGetterSetter = new PosmMasterGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("META_DATA")) {
                        posmMasterGetterSetter.setTable_POSMMaster(xpp.nextText());
                    }
                    if (xpp.getName().equals("POSM_CD")) {
                        posmMasterGetterSetter.setPOSM_CD(xpp.nextText());
                    }
                    if (xpp.getName().equals("POSM")) {
                        posmMasterGetterSetter.setPOSM(xpp.nextText());
                    }
                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            Log.e("error in posmMasterXmlHandler", e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.e("error in posmMasterXmlHandler", e.toString());
            e.printStackTrace();
        }
        return posmMasterGetterSetter;
    }

    // JCP XML HANDLER FOR NON WORKING REASON
    public static StateMasterGetterSetter stateMasterXML(XmlPullParser xpp, int eventType) {
        StateMasterGetterSetter statemaster = new StateMasterGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("META_DATA")) {
                        statemaster.setTable_STATE_MASTER(xpp.nextText());
                    }
                    if (xpp.getName().equals("STATE_CD")) {
                        statemaster.setSTATE_CD(xpp.nextText());
                    }
                    if (xpp.getName().equals("STATE")) {
                        statemaster.setSTATE(xpp.nextText());
                    }
                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return statemaster;
    }

    // STORE TYPE MASTER XML HANDLER
    public static StoreTypeMasterGetterSetter storeTypeXmlHandler(XmlPullParser xpp, int eventType) {
        StoreTypeMasterGetterSetter storetype = new StoreTypeMasterGetterSetter();
        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("META_DATA")) {
                        storetype.setTable_STORETYPE_MASTER(xpp.nextText());
                    }
                    if (xpp.getName().equals("STORETYPE_CD")) {
                        storetype.setSTORETYPE_CD(xpp.nextText());
                    }
                    if (xpp.getName().equals("STORETYPE")) {
                        storetype.setSTORETYPE(xpp.nextText());
                    }
                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return storetype;
    }

    // Window Master
    public static WindowMasterGetterSetter windowMasterXmlHandler(XmlPullParser xpp, int eventType) {
        WindowMasterGetterSetter windowmaster = new WindowMasterGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("META_DATA")) {
                        windowmaster.setTable_WINDOW_MASTER(xpp.nextText());
                    }
                    if (xpp.getName().equals("WINDOW_CD")) {
                        windowmaster.setWINDOW_CD(xpp.nextText());
                    }
                    if (xpp.getName().equals("WINDOW")) {
                        windowmaster.setWINDOW(xpp.nextText());
                    }
                    if (xpp.getName().equals("SKU_HOLD")) {
                        windowmaster.setSKU_HOLD(xpp.nextText());
                    }
                    if (xpp.getName().equals("PLANOGRAM_IMAGE")) {
                        windowmaster.setPLANOGRAM_IMAGE(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return windowmaster;
    }

    // window Checklist Master
    public static WindowChecklistGetterSetter windowChecklistXmlHandler(XmlPullParser xpp, int eventType) {
        WindowChecklistGetterSetter windowChecklist = new WindowChecklistGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("META_DATA")) {
                        windowChecklist.setTable_WINDOW_CHECKLIST(xpp.nextText());
                    }
                    if (xpp.getName().equals("CHECKLIST_CD")) {
                        windowChecklist.setCHECKLIST_CD(xpp.nextText());
                    }
                    if (xpp.getName().equals("CHECKLIST")) {
                        windowChecklist.setCHECKLIST(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return windowChecklist;
    }

    //mappingWindowChecklist XmlHandler

    public static MappingWindowChecklistGetterSetter mappingWindowChecklistXmlHandler(XmlPullParser xpp, int eventType) {
        MappingWindowChecklistGetterSetter mappingWindowChecklist = new MappingWindowChecklistGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("META_DATA")) {
                        mappingWindowChecklist.setTable_MAPPING_WINDOW_CHECKLIST(xpp.nextText());
                    }
                    if (xpp.getName().equals("WINDOW_CD")) {
                        mappingWindowChecklist.setWINDOW_CD(xpp.nextText());
                    }
                    if (xpp.getName().equals("CHECKLIST_CD")) {
                        mappingWindowChecklist.setCHECKLIST_CD(xpp.nextText());
                    }
                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mappingWindowChecklist;
    }


    //WINDOW CHECKLIST ANSWER XML HANDLER
    public static WindowChecklistAnswerGetterSetter windowChecklistAnswerXmlHandler(XmlPullParser xpp, int eventType) {
        WindowChecklistAnswerGetterSetter windowchecklistanswer = new WindowChecklistAnswerGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("META_DATA")) {
                        windowchecklistanswer.setTable_WINDOW_CHECKLIST_ANSWER(xpp.nextText());
                    }
                    if (xpp.getName().equals("ANSWER_CD")) {
                        windowchecklistanswer.setANSWER_CD((xpp.nextText()));
                    }
                    if (xpp.getName().equals("ANSWER")) {
                        windowchecklistanswer.setANSWER(xpp.nextText());
                    }
                    if (xpp.getName().equals("CHECKLIST_CD")) {
                        windowchecklistanswer.setCHECKLIST_CD(xpp.nextText());
                    }
                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return windowchecklistanswer;
    }


    //JCP XML HANDLER FOR Order_Delivery
    public static WindowMappingGetterSetter windowMappingXmlHandler(XmlPullParser xpp, int eventType) {
        WindowMappingGetterSetter windowMapping = new WindowMappingGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("META_DATA")) {
                        windowMapping.setTable_WINDOW_MAPPING(xpp.nextText());
                    }
                    if (xpp.getName().equals("STATE_CD")) {
                        windowMapping.setSTATE_CD(xpp.nextText());
                    }
                    if (xpp.getName().equals("STORETYPE_CD")) {
                        windowMapping.setSTORETYPE_CD(xpp.nextText());
                    }
                    if (xpp.getName().equals("WINDOW_CD")) {
                        windowMapping.setWINDOW_CD(xpp.nextText());
                    }
                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return windowMapping;
    }
}
