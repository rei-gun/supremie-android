package model;

/**
 * Created by Taslim_Hartmann on 5/13/2017.
 */

public class User {
    private String userName;
    private String pin;
    private String aggregatorId;
    private String privateKey;
    private String publicKey;

    public User() {
        this.publicKey = "<RSAPublicKey>\n" +
                "\t<Modulus>sV+bylVaUrz/rCEXl0YlU72Np3aKewfkyQXzvyzmXUwImWTUB5IQPzSbFcQSelRp0fvBYe9za2s3NTtWPivJYYcOQtmvTJxXK4XJNcvNtFnFh17jtsyENa9+BSREX0jaWWv+pNZjFP30BFGaPztrGQC8GUvVSkJnPgi/kdcQVHI0Ht5fi59tY4vPe5z4HjeNnZJwTcidmrHZJEzOnhoeUaTNNEGuapjzYhEoRTTBuQBc3e7aqE+cs9WHeI1UdGMe+AHiTnxAN7wyTW5o5nwEqKb195cRwwKlU92WUIkQbglLwEEF0XbClO6vtVgFl47yOIYVo1VpGS/+p54dEHCZJw==</Modulus>\n" +
                "\t<Exponent>AQAB</Exponent>\n" +
                "</RSAPublicKey>";
        this.privateKey = "<RSAKeyValue>\n" +
                "\t<Modulus>v5vIrVt2JRvCuKVoaO0X9sPoUxFePsc1XwJFRBZAapEapDbiW3Gfq396Kgf94AZlRJyJpK29huONF0mr33JYziUy11URWypev8KCp2H1a+6oj7HKUl/77u7NcikIsJMFYHYmYtoQouXvnYSVBAfS8m8dag+TWmmQgaBlMAsLH8LZ5SVAGJ6K5SjTV4I4LTpB1SKYxz9r6nDMUtFUovu6IQLh8vScyUBaGdP4a4iayhzx9+P33+2dpU6cpzNAVt3iBw4QCT44J3oBh3u1maOmw3EE/1JB12rJgPx2oYvp31AM8Hz2/e4i0EpPwc0VFBNT88XjACmK4aymn7+yGoskSQ==</Modulus>\n" +
                "\t<Exponent>AQAB</Exponent>\n" +
                "\t<D>WTN/EyWXNONdnXRzzRVDqL+/cpyNUZCQCjEWagIp7vKqgnSCoKue98oa52JdEGQp1O1DLwUrTJa24naLqzSinyW2K4UQ1W4+Oeulw6LZDGxI0MymMf85XByUTGdKAloPkL6wVCjMwellyeElNoEl9XNBMFUb9mVavt4a/WjGvvu5+63lTsxkmXhezGI95EBewVg6Nq06QENp//kKTS6rq26u/cz50T7RcFRGnALjtf3vMzRqZ19VLzrB1InMOR9AV1btPJ1LRErPiAJzTAajBigFeKQDbygdS+ejOaO+5KXaFGnHGZVQGVgPGIM9UMHwe5BHMy1z/+J/v/NJf6xFmQ==</D>\n" +
                "\t<P>x5SVj29CJI7zCQ0NFUfyRLZEuegnjn7QMCezqL91kzSoc1ktufMvvn+BgY8CWDH2YRfe/bQPKRcKUihe/cO9B9O5EDpmFrbEN9HytS6ihs+57HFjDAzATxcSFo6UFc+pkaE69W8CeydjbI5l1TNXbRINZBge2thznSb2z6ybF0c=</P>\n" +
                "\t<Q>9cZIoSIJ0tipE3fXrJ3GBnhezkAT2Kzxfny/pJ7c/RHSt6mrTPoBWQnJVfNiiO87cuCPRqEOOisoCf5jNtqFT2ncYvkYZ4GKizVXovmp2lHEHxOraacxobLj0jGNacO5n4WHr7zUt6noT2tMnnDkb2+eIDJlAw8gK4jExxWiz+8=</Q>\n" +
                "\t<DP>hAIPerQId83litMLGExiPnigtKaNo37VjUR1XORmjFuEH+eYiyoDJmmH633/+v3xDP+RCxYSAQewV+9EBW12lrq/6a/23oc8+f7wHk8eSTUUsQO+k7XLbYTA/hKIeHTGoACY0sX+Sh8fcCovyPkQlqAeI1qg7ffFYOjozyxH2Pk=</DP>\n" +
                "\t<DQ>VjwrcbYywjNZJ8aN+zgkqlKD2VRsYkNUPLBiwc0n9vfQ+rZir5Cs9mPTq//I3Aksz8LxIrQR/OD1nIQQpceIrPOU63lBUdgHF48w37NAIKU97jWFmH2ckYLmIvNWDf0p3UoJ8OSNsp/ALlWUvacYz+zXC68C+E7PV9cWTFBQHys=</DQ>\n" +
                "\t<InverseQ>bzFySi/UMEB8bH+c+sNgZzAn/UkAfSHcv2/cbSYz4x/JkrewylJC6Iw6xLRl6GQS96VYT48CL1Juj318IwLK0DCbcfigBZD/LGTFCbJLq+sWW0+TX2R2lbkwjI2i6JTan9qmsoY4Kf3wMY7YBKoy1DPuGtLa4VMMdW4yPBniPS8=</InverseQ>\n" +
                "</RSAKeyValue>";
        this.userName = "supremie1";
        this.aggregatorId = "supremie";
    }

    public User(String userName, String pin) {
        this.userName = userName;
        this.pin = pin;
    }

    public User(String publicKey, String privateKey, String userName, String aggregatorId) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.userName = userName;
        this.aggregatorId = aggregatorId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getAggregatorId() {
        return aggregatorId;
    }

    public void setAggregatorId(String aggregatorId) {
        this.aggregatorId = aggregatorId;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
