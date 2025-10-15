package com.darsh.news.data.remote.data_model

data class News (
    val articles : Array<Article> ,
    val totalResults: Int,
    val status: String,
)

/**
 * Represents a news category for API requests.
 * Use `.name.lowercase()` to get the string value for the API call.
 */
enum class Category {
    BUSINESS,
    ENTERTAINMENT,GENERAL,
    HEALTH,
    SCIENCE,
    SPORTS,
    TECHNOLOGY
}

/**
 * Represents the 2-letter ISO 3166-1 code for a country.
 * The enum's `name` (e.g., "US") is used as the value for the API call.
 */
//enum class Country {
//    AE, // United Arab Emirates
//    AR, // Argentina
//    AT, // Austria
//    AU, // Australia
//    BE, // Belgium
//    BG, // Bulgaria
//    BR, // Brazil
//    CA, // Canada
//    CH, // Switzerland
//    CN, // China
//    CO, // Colombia
//    CU, // Cuba
//    CZ, // Czech Republic
//    DE, // Germany
//    EG, // Egypt
//    FR, // France
//    GB, // United Kingdom
//    GR, // Greece
//    HK, // Hong Kong
//    HU, // Hungary
//    ID, // Indonesia
//    IE, // Ireland
//    IL, // Israel
//    IN, // India
//    IT, // Italy
//    JP, // Japan
//    KR, // South Korea
//    LT, // Lithuania
//    LV, // Latvia
//    MA, // Morocco
//    MX, // Mexico
//    MY, // Malaysia
//    NG, // Nigeria
//    NL, // Netherlands
//    NO, // Norway
//    NZ, // New Zealand
//    PH, // Philippines
//    PL, // Poland
//    PT, // Portugal
//    RO, // Romania
//    RS, // Serbia
//    RU, // Russia
//    SA, // Saudi Arabia
//    SE, // Sweden
//    SG, // Singapore
//    SI, // Slovenia
//    SK, // Slovakia
//    TH, // Thailand
//    TR, // Turkey
//    TW, // Taiwan
//    UA, // Ukraine
//    US, // United States
//    VE, // Venezuela
//    ZA  // South Africa
//}

