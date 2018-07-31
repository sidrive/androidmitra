package lawyerku.mitra.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CredentialModel {

    public static class Request {

        public String name;
        //        @SerializedName("cellphone_number_1") public String cellphone1;
//        @SerializedName("cellphone_number_2") public String cellphone2;
        @SerializedName("id_number") public String nik;

        // ====== Request for Register Customer
        public String address;
        @SerializedName("phone_number") public String phoneNumber;
        public String email;

        // ====== Request for Register Workman
        public int level;
//        @SerializedName("have_smartphone") public boolean haveSmartphone;
        @SerializedName("address_1") public String address1;
//        @SerializedName("address_2") public String address2;
        @SerializedName("gps_latitude") public Double latitude;
        @SerializedName("gps_longitude") public Double longitude;
        @SerializedName("rate_min") public int rateMin;
        @SerializedName("rate_max") public int rateMax;
        @SerializedName("rate_is_negotiable") public int rateNego;
//        public List<Integer> languages;
//        public List<Integer> jobs;

        // ====== Request for Login
        public String username;
        public String password;
        public String c_password;
        public String first_name;
        public String last_name;
        public String role_id;
        public String cellphone_number;
        public String cellphone_number_2;


        @Override
        public String toString() {
            return "Request{" +
                    "name='" + name + '\'' +
                    ", nik='" + nik + '\'' +
                    ", address='" + address + '\'' +
                    ", phoneNumber='" + phoneNumber + '\'' +
                    ", email='" + email + '\'' +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", c_password='" + c_password + '\'' +
                    ", first_name='" + first_name + '\'' +
                    ", last_name='" + last_name + '\'' +
                    ", role_id='" + role_id + '\'' +
                    ", cellphone_number='" + cellphone_number + '\'' +
                    ", cellphone_number_2='" + cellphone_number_2 + '\'' +
                    '}';
        }
    }

    public static class RegistrationResponse {
        public int status;
        public Success data;
        public String message;
        public String error;

        @Override
        public String toString() {
            return "RegistrationResponse{" +
                    "status=" + status +
                    ", message=" + message +
                    ", data=" + data +
                    ", error='" + error + '\'' +
                    '}';
        }
    }



    public static class Success{
        public String username;
        public int id;
        public String email;
        public int role_id;
        public int userable_id;
        public String userable_type;
        public String update_at;
        public String created_at;
        public String token;

        @Override
        public String toString() {
            return "Success{" +
                    "username='" + username + '\'' +
                    ", id=" + id +
                    ", email='" + email + '\'' +
                    ", role_id=" + role_id +
                    ", userable_id=" + userable_id +
                    ", userable_type='" + userable_type + '\'' +
                    ", update_at='" + update_at + '\'' +
                    ", created_at='" + created_at + '\'' +
                    '}';
        }
    }

    public static class LoginResponse {
        // Response for Login
        public int status;
        public String accessToken;
        public String message;
        public String userType;
        public Success success;
        public Data data;

        @Override
        public String toString() {
            return "LoginResponse{" +
                    "status=" + status +
                    ", accessToken='" + accessToken + '\'' +
                    ", message='" + message + '\'' +
                    ", userType='" + userType + '\'' +
                    ", success=" + success +
                    ", data=" + data +
                    '}';
        }
    }

    public static class Error{
        public List<String> id_number;
        public List<String> email;
        public List<String> name;
        public List<String> address;
        public List<String> phone_number;
        public List<String> cellphone_number_1;
        public List<String> cellphone_number_2;
        public List<String> level;

        public List<String> address_1;
        public List<String> address_2;
        public List<String> gps_latitude;
        public List<String> gps_longitude;
        public List<String> rate_min;
        public List<String> rate_max;
        public List<String> languages;
        public List<String> jobs;
        public List<String> username;
        public List<String> password;
    }

    public static class Data{
        public String token;

        @Override
        public String toString() {
            return "Data{" +
                    "token='" + token + '\'' +
                    '}';
        }
    }
}
