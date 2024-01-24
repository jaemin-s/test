package com.castis.pvs.api.model.v3;

public enum MsoType {
    LGHV("LGHV", "LGHV", 4003, "lghvottrestfulapiservice20230815", "asset/msologo/LGHV.png","1855-2135","1855-2135"),
    SKB("SKB", "SKB", 4004, "skbottrestfulapiservice20230815s", "asset/msologo/SKB.png","1877-7000","1877-7000"),
    dlive("dlive", "딜라이브", 4002, "dliveottrestfulapiservi20230815j", "asset/msologo/dlive.png","1644-1100","1644-1100"),
    CMB("CMB", "CMB", 4005, "Cmbottrestfulapiservice20230317s", "asset/msologo/CMB.png","1544-3434","1544-3434"),
    HCN("HCN", "HCN", 4012, "Hcnottrestfulapiservice20221111s", "asset/msologo/HCN.png","1877-8000 (유료) 070-8100-1000 (무료)","070-8100-1000"),
    KCN("KCN", "금강방송", 4008, "kcnottrestfulapiservice20230815s", "asset/msologo/KCN.png","1544-5400","1544-5400"),
    GCS("GCS", "푸른방송", 4015, "gcsottrestfulapiservice20230809s", "asset/msologo/GCS.png","053-551-2000","053-551-2000"),
    nibtv("nibtv", "남인천방송", 4018, "nibtvottrestfulapiservi20230815n", "asset/msologo/nibtv.png","1544-0777","1544-0777"),
    kctvjeju("kctvjeju", "KCTV제주방송", 4006, "kctvjejuottrestfulapisv20230815s", "asset/msologo/kctvjeju.png","064-741-7777","064-741-7777"),
    ccstv("ccstv", "씨씨에스충북방송", 4013, "ccstvottrestfulapiservice2023062", "asset/msologo/ccstv.png","043-850-7000 (가입상담) 043-850-7000 (대표번호)","043-850-7000"),
    kctv("kctv", "KCTV광주방송", 4022, "kctvottrestfulapiservice20230815", "asset/msologo/kctv.png","062-417-8000","062-417-8000"),
    iscs("iscs", "서경방송", 4019, "iscsottrestfulapiservice20230710", "asset/msologo/iscs.png","055-740-3001","055-740-3001"),
    jcntv("jcntv", "JCN울산중앙방송", 4021, "jcntvottrestfulapiservi20230815j", "asset/msologo/jcntv.png","1877-9100","1877-9100"),
    abn("abn", "아름방송", 4017, "jcntvottrestfulapiservi20230815j", "asset/msologo/abn.png","1544-1100","1544-1100"),
    ochoice("ochoice", "OH", 0000, "", "asset/msologo/ochoice.png","1544-1100","1544-1100");

    MsoType(String msoTitle, String mosName, int msoId, String encrypt_key, String mosImagePath, String serviceCenter, String serviceCenter_2) {
        this.msoTitle = msoTitle;
        this.mosName = mosName;
        this.msoId = msoId;
        this.encrypt_key = encrypt_key;
        this.mosImagePath = mosImagePath;
        this.serviceCenter = serviceCenter;
        this.serviceCenter_2 = serviceCenter_2;
    }
    public final String msoTitle;
    public final String mosName;
    public final int msoId;
    public final String encrypt_key;
    public final String mosImagePath;
    public final String serviceCenter;
    public final String serviceCenter_2;

    public static MsoType getMsoType(String mosName){
        for (MsoType type : MsoType.values()) {
            if (type.mosName.equals(mosName)) {
                return type;
            }
        }

        return MsoType.ochoice;
    }
    public static MsoType getMsoTypeForId(int msoId){
        for (MsoType type : MsoType.values()) {
            if (type.msoId == msoId) {
                return type;
            }
        }

        return MsoType.ochoice;
    }
}
