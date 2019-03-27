package com.qunli.network;

import java.io.Serializable;

class ResponBean implements Serializable {
    private String _id;
    private String create;
    private int category;
    private From from;
    private String content;
    private String __v;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public From getFrom() {
        return from;
    }

    public void setFrom(From from) {
        this.from = from;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }

    @Override
    public String toString() {
        return "ResponBean{" +
                "_id='" + _id + '\'' +
                ", create='" + create + '\'' +
                ", category=" + category +
                ", from=" + from +
                ", content='" + content + '\'' +
                ", __v='" + __v + '\'' +
                '}';
    }

    class From implements Serializable {
        private String _id;
        private String photo;
        private String nickname;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        @Override
        public String toString() {
            return "From{" +
                    "_id='" + _id + '\'' +
                    ", photo='" + photo + '\'' +
                    ", nickname='" + nickname + '\'' +
                    '}';
        }
    }
}
