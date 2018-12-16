package com.example.jf.myapp.Model;



public class Zone {
        private String userName;                  //用户名
        private String content;                  //发布内容
        private String img;

        public Zone(){}
        public Zone(String userName, String content){
            this.userName  = userName;
            this.content = content;
        }


        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

    }
