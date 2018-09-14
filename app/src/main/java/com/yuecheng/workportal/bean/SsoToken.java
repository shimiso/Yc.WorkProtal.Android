package com.yuecheng.workportal.bean;

/**
 * 描述:
 * 作者: Shims
 * 时间: ${date} ${time}
 */
public class SsoToken {

    /**
     * access_token : eyJhbGciOiJSUzI1NiIsImtpZCI6IjUwNjM5ODdiODdiY2Q5OTBjYThkNTI4YmYzNzU0MTI4IiwidHlwIjoiSldUIn0.eyJuYmYiOjE1MzYyMjcxMTAsImV4cCI6MTUzNjIzMDcxMCwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo1MDAwIiwiYXVkIjpbImh0dHA6Ly9sb2NhbGhvc3Q6NTAwMC9yZXNvdXJjZXMiLCJhcGkxIl0sImNsaWVudF9pZCI6InJvLmNsaWVudCIsInN1YiI6IjEiLCJhdXRoX3RpbWUiOjE1MzYyMjcxMTAsImlkcCI6ImxvY2FsIiwic2NvcGUiOlsiYXBpMSJdLCJhbXIiOlsicHdkIl19.v0bAgCNsh7vtkQc3L1tnvHVTMAcJ-v-iMIa6dVTMR3pMow99btngzuDSvEgFitKQFIGmFEU_rK7YHBXQ2TAOddbXgrcVon46e1yokqqPY4VBeXa3SRWCUVqHli39ahqoPWEKNX7n84xtyKsTl33kPTMv3CNBH29OmSTzqzhZcvxDgUTXGh7ywe3H12rTlqC_L7Y1iiigOJI_l-BI6PiJAlEAzD3QyBlQIxqpfQdWTDK51N0QCoWwsaeTh_UqtgVp3M1SV_2kgf5_fE7dtxub9OsgYtgcdkLl8JzABg7a06zWns-SOYN1ug9mM0axhGcU-_C1lEXS4JPLzsyBWvvTdQ
     * expires_in : 3600
     * token_type : Bearer
     */

    private String access_token;
    private int expires_in;
    private String token_type;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }
}
