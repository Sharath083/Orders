package com.task.orders.constants;

public class TestConstants {

    public static final String PRODUCT_ID = "223be1cb-a90f-4bda-b9bf-d59df6be628e";
    public static final String PRODUCT_ID_INVALID ="223be1cb-a90f-4bda-b9bf-d59df6be627e" ;
    public static final String ORDER_ID = "223be1cb-a90f-4bda-b9bf-d59df6be628f";
    public static final String ORDER_ID_INVALID = "223be1cb-a90f-4bda-b9bf-d59df6be62ff";
    public static final String USER_ID = "1ec8ac90-2fb7-469c-bfd9-cff4fbc92277";
    public static final String USER_ID_INVALID = "1ec8ac90-2fb7-469c-bfd9-cff4fbc92278";
    public static final String SESSION_ID = "MWVjOGFjOTAtMmZiNy00NjljLWJmZDktY2ZmNGZiYzkyMjc3Ly9hQGdtYWlsLmNvbS8vc2hhcmF0aA==";
    public static final String TEST_URL = "http://localhost:8080/test";
    public static String mockResponse= """
            {
                "head": {
                    "status": "0",
                    "statusDescription": "Success",
                    "responseCode": "Trendlyne"
                },
                "body": {
                    "dates_info": {
                        "start_date": "2024-06-03",
                        "end_date": "2024-06-07"
                    },
                    "tableData": [
                        {
                            "client_type": "Future Index Long",
                            "retail_clients": 424213.0,
                            "retail_change_percent": -8.703072406876544,
                            "dii": 109544.0,
                            "dii_change_percent": -9.830681472091664,
                            "fii": 139931.0,
                            "fii_change_percent": 9.306576470312537,
                            "pro": 126237.0,
                            "pro_change_percent": 62.932703477116085,
                            "total": 799925.0,
                            "total_change_percent": 1.0473274265632855
                        },
                        {
                            "client_type": "Future Index Short",
                            "retail_clients": 301205.0,
                            "retail_change_percent": 9.11050334172538,
                            "dii": 102061.0,
                            "dii_change_percent": -9.334719150032425,
                            "fii": 332773.0,
                            "fii_change_percent": 2.4039807853865542,
                            "pro": 63886.0,
                            "pro_change_percent": -18.146292713551745,
                            "total": 799925.0,
                            "total_change_percent": 1.0473274265632855
                        },
                        {
                            "client_type": "Net Future Index",
                            "retail_clients": 123008.0,
                            "retail_change_percent": -34.77732943790198,
                            "dii": 7483.0,
                            "dii_change_percent": -16.09105180533752,
                            "fii": -192842.0,
                            "fii_change_percent": 2.0828255747826794,
                            "pro": 62351.0,
                            "pro_change_percent": 11019.614711033275,
                            "total": 0.0,
                            "total_change_percent": 0
                        },
                        {
                            "client_type": "Future Stock Long",
                            "retail_clients": 1969331.0,
                            "retail_change_percent": -7.926956129823146,
                            "dii": 170658.0,
                            "dii_change_percent": 28.79751850929427,
                            "fii": 2177792.0,
                            "fii_change_percent": 2.1999490359296696,
                            "pro": 540022.0,
                            "pro_change_percent": 7.650874528049884,
                            "total": 4857803.0,
                            "total_change_percent": -0.9407139368690653
                        },
                        {
                            "client_type": "Future Stock Short",
                            "retail_clients": 205414.0,
                            "retail_change_percent": 9.31277073554924,
                            "dii": 2865828.0,
                            "dii_change_percent": -2.860631213301503,
                            "fii": 1562694.0,
                            "fii_change_percent": 0.03149400653692672,
                            "pro": 223867.0,
                            "pro_change_percent": 9.956482445627616,
                            "total": 4857803.0,
                            "total_change_percent": -0.9407139368690653
                        },
                        {
                            "client_type": "Net Future Stock",
                            "retail_clients": 1763917.0,
                            "retail_change_percent": -9.587460564387367,
                            "dii": -2695170.0,
                            "dii_change_percent": 4.349328996969892,
                            "fii": 615098.0,
                            "fii_change_percent": 8.156515347865612,
                            "pro": 316155.0,
                            "pro_change_percent": 6.075907745784208,
                            "total": 0.0,
                            "total_change_percent": 0
                        },
                        {
                            "client_type": "Option Index Call Long",
                            "retail_clients": 5373262.0,
                            "retail_change_percent": -8.351980143726697,
                            "dii": 2078.0,
                            "dii_change_percent": -46.06799896184791,
                            "fii": 1330767.0,
                            "fii_change_percent": -1.5298473778378947,
                            "pro": 1812191.0,
                            "pro_change_percent": -3.829486210644296,
                            "total": 8518298.0,
                            "total_change_percent": -6.418861465650398
                        },
                        {
                            "client_type": "Option Index Call Short",
                            "retail_clients": 5298415.0,
                            "retail_change_percent": 9.810139502668251,
                            "dii": 0.0,
                            "dii_change_percent": 0,
                            "fii": 1308006.0,
                            "fii_change_percent": -35.8768723940907,
                            "pro": 1911877.0,
                            "pro_change_percent": -14.55970390708932,
                            "total": 8518298.0,
                            "total_change_percent": -6.418861465650398
                        },
                        {
                            "client_type": "Net Option Index Call",
                            "retail_clients": 74847.0,
                            "retail_change_percent": -92.78836148088767,
                            "dii": 2078.0,
                            "dii_change_percent": -46.06799896184791,
                            "fii": 22761.0,
                            "fii_change_percent": 103.30639620100727,
                            "pro": -99686.0,
                            "pro_change_percent": 71.78623586283412,
                            "total": 0.0,
                            "total_change_percent": 0
                        },
                        {
                            "client_type": "Option Index Put Long",
                            "retail_clients": 4895835.0,
                            "retail_change_percent": 6.495486514358777,
                            "dii": 247311.0,
                            "dii_change_percent": 29.662096626209138,
                            "fii": 1551868.0,
                            "fii_change_percent": -4.0875646243367605,
                            "pro": 1904135.0,
                            "pro_change_percent": 1.4893465061145128,
                            "total": 8599149.0,
                            "total_change_percent": 3.8274338019513037
                        },
                        {
                            "client_type": "Option Index Put Short",
                            "retail_clients": 5231353.0,
                            "retail_change_percent": 22.909851202419407,
                            "dii": 0.0,
                            "dii_change_percent": 0,
                            "fii": 1377851.0,
                            "fii_change_percent": -31.782800277255173,
                            "pro": 1989945.0,
                            "pro_change_percent": -0.8054421931476101,
                            "total": 8599149.0,
                            "total_change_percent": 3.8274338019513037
                        },
                        {
                            "client_type": "Net Option Index Put",
                            "retail_clients": -335518.0,
                            "retail_change_percent": -198.40074375826683,
                            "dii": 247311.0,
                            "dii_change_percent": 29.662096626209138,
                            "fii": 174017.0,
                            "fii_change_percent": 143.30989683793973,
                            "pro": -85810.0,
                            "pro_change_percent": 33.94708685176775,
                            "total": 0.0,
                            "total_change_percent": 0
                        },
                        {
                            "client_type": "Option Stock Call Long",
                            "retail_clients": 1474955.0,
                            "retail_change_percent": 52.477551518309234,
                            "dii": 0.0,
                            "dii_change_percent": 0,
                            "fii": 150657.0,
                            "fii_change_percent": 96.7289536569123,
                            "pro": 513338.0,
                            "pro_change_percent": 31.65753621404243,
                            "total": 2138950.0,
                            "total_change_percent": 49.17935488010623
                        },
                        {
                            "client_type": "Option Stock Call Short",
                            "retail_clients": 838883.0,
                            "retail_change_percent": 42.259028942472874,
                            "dii": 237897.0,
                            "dii_change_percent": 58.17409342960865,
                            "fii": 183451.0,
                            "fii_change_percent": 100.82870811301959,
                            "pro": 878719.0,
                            "pro_change_percent": 45.87574185515667,
                            "total": 2138950.0,
                            "total_change_percent": 49.17935488010623
                        },
                        {
                            "client_type": "Net Option Stock Call",
                            "retail_clients": 636072.0,
                            "retail_change_percent": 68.43387467925717,
                            "dii": -237897.0,
                            "dii_change_percent": -58.17409342960865,
                            "fii": -32794.0,
                            "fii_change_percent": -122.09129080319653,
                            "pro": -365381.0,
                            "pro_change_percent": -71.96746850158375,
                            "total": 0.0,
                            "total_change_percent": 0
                        },
                        {
                            "client_type": "Option Stock Put Long",
                            "retail_clients": 475945.0,
                            "retail_change_percent": 38.49742033295601,
                            "dii": 85.0,
                            "dii_change_percent": -59.71563981042654,
                            "fii": 138895.0,
                            "fii_change_percent": 61.39508941540106,
                            "pro": 594062.0,
                            "pro_change_percent": 33.42062413673064,
                            "total": 1208987.0,
                            "total_change_percent": 38.142472239806025
                        },
                        {
                            "client_type": "Option Stock Put Short",
                            "retail_clients": 675356.0,
                            "retail_change_percent": 29.08575185785498,
                            "dii": 0.0,
                            "dii_change_percent": 0,
                            "fii": 126472.0,
                            "fii_change_percent": 116.29497879326857,
                            "pro": 407159.0,
                            "pro_change_percent": 38.71687596672095,
                            "total": 1208987.0,
                            "total_change_percent": 38.142472239806025
                        },
                        {
                            "client_type": "Net Option Stock Put",
                            "retail_clients": -199411.0,
                            "retail_change_percent": -11.070821845322639,
                            "dii": 85.0,
                            "dii_change_percent": -59.71563981042654,
                            "fii": 12423.0,
                            "fii_change_percent": -54.96791967230942,
                            "pro": 186903.0,
                            "pro_change_percent": 23.17562624804761,
                            "total": 0.0,
                            "total_change_percent": 0
                        },
                        {
                            "client_type": "Total Long Contracts",
                            "retail_clients": 14613541.0,
                            "retail_change_percent": 1.66180603063919,
                            "dii": 529676.0,
                            "dii_change_percent": 18.023917805105764,
                            "fii": 5489910.0,
                            "fii_change_percent": 1.834403415904643,
                            "pro": 5489985.0,
                            "pro_change_percent": 6.090295262272739,
                            "total": 26123112.0,
                            "total_change_percent": 2.8902857421489827
                        },
                        {
                            "client_type": "Total Short Contracts",
                            "retail_clients": 12550626.0,
                            "retail_change_percent": 17.75601813483583,
                            "dii": 3205786.0,
                            "dii_change_percent": -0.23054941593940484,
                            "fii": 4891247.0,
                            "fii_change_percent": -19.771128807993023,
                            "pro": 5475453.0,
                            "pro_change_percent": 0.9985765451457644,
                            "total": 26123112.0,
                            "total_change_percent": 2.8902857421489827
                        },
                        {
                            "client_type": "Net Total Contracts",
                            "retail_clients": 2062915.0,
                            "retail_change_percent": -44.493086373446424,
                            "dii": -2676110.0,
                            "dii_change_percent": 3.1940665755802238,
                            "fii": 598663.0,
                            "fii_change_percent": 184.84452947845804,
                            "pro": 14532.0,
                            "pro_change_percent": 105.8954781860816,
                            "total": 0.0,
                            "total_change_percent": 0
                        }
                    ]
                }
            }
            """;
}
