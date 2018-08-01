package lawyerku.mitra.api.model;

import com.google.gson.annotations.SerializedName;

import java.security.PublicKey;
import java.util.List;

public class LawyerModel {
    public static class Response {
        public int status;
        public String message;
        public List<Data> data;

        @Override
        public String toString() {
            return "Response{" +
                    "status=" + status +
                    ", message='" + message + '\'' +
                    ", data=" + data +
                    '}';
        }
    }

    public static class ResponseUpdate {
        public int status;
        public String message;
        public Data data;

        @Override
        public String toString() {
            return "Response{" +
                    "status=" + status +
                    ", message='" + message + '\'' +
                    ", data=" + data +
                    '}';
        }
    }

    public static class DataUpdate {
        public int id;
        public String name;
        public String first_name;
        public String last_name;
        public String address_1;
        public String address_2;
        @SerializedName("gps_latitude") public String latitude;
        @SerializedName("gps_longitude") public String longitude;
        @SerializedName("cellphone_number_1") public String cellphone1;
        @SerializedName("cellphone_number_2") public String cellphone2;
        public int level;
        @SerializedName("have_smartphone") public boolean smartphone;
        @SerializedName("rate_min") public String rateMin;
        @SerializedName("rate_max") public String rateMax;
        @SerializedName("id_number") public String idnumber;
        public int languageskill;
        public int jobskill;
        public String email;

        @Override
        public String toString() {
            return "DataUpdate{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", first_name='" + first_name + '\'' +
                    ", last_name='" + last_name + '\'' +
                    ", address_1='" + address_1 + '\'' +
                    ", address_2='" + address_2 + '\'' +
                    ", latitude='" + latitude + '\'' +
                    ", longitude='" + longitude + '\'' +
                    ", cellphone1='" + cellphone1 + '\'' +
                    ", cellphone2='" + cellphone2 + '\'' +
                    ", level=" + level +
                    ", smartphone=" + smartphone +
                    ", rateMin='" + rateMin + '\'' +
                    ", rateMax='" + rateMax + '\'' +
                    ", idnumber='" + idnumber + '\'' +
                    ", languageskill=" + languageskill +
                    ", jobskill=" + jobskill +
                    ", email='" + email + '\'' +
                    '}';
        }
    }

    public static class Data {
        public int id;
        public String name;
        public String first_name;
        public String last_name;
        public String address_1;
        public String address_2;
        @SerializedName("gps_latitude") public String latitude;
        @SerializedName("gps_longitude") public String longitude;
        @SerializedName("cellphone_number_1") public String cellphone1;
        @SerializedName("cellphone_number_2") public String cellphone2;
        public int level;
        @SerializedName("have_smartphone") public boolean smartphone;
        @SerializedName("rate_min") public String rateMin;
        @SerializedName("rate_max") public String rateMax;
        @SerializedName("id_number") public String idnumber;
        public double rating;
        public String comment;
        public double distance;


        public Image images;
        public List<Language> languageskills;
        public List<Skill> jobskills;
        public User user;

        public static class Image {
            @SerializedName("small_url") public String smallUrl;
            @SerializedName("medium_url") public String mediumUrl;
            @SerializedName("large_url") public String largeUrl;
        }

        public static class Language {
            public int id;
            public String name;

            @Override
            public String toString() {
                return "Language{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        '}';
            }
        }

        public static class Skill {
            public int id;
            public String name;

            @Override
            public String toString() {
                return "Skill{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        '}';
            }
        }

        public static class User{
            public int id;
            public String username;
            public String email;
            public int userable_id;
            public int role_id;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", address_1='" + address_1 + '\'' +
                    ", address_2='" + address_2 + '\'' +
                    ", latitude='" + latitude + '\'' +
                    ", longitude='" + longitude + '\'' +
                    ", cellphone1='" + cellphone1 + '\'' +
                    ", cellphone2='" + cellphone2 + '\'' +
                    ", level=" + level +
                    ", smartphone=" + smartphone +
                    ", rateMin='" + rateMin + '\'' +
                    ", rateMax='" + rateMax + '\'' +
                    ", idnumber='" + idnumber + '\'' +
                    ", rating=" + rating +
                    ", comment='" + comment + '\'' +
                    ", distance=" + distance +
                    ", images=" + images +
                    ", languageskills=" + languageskills +
                    ", jobskills=" + jobskills +
                    '}';
        }
    }
}
