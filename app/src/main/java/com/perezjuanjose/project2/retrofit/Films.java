package com.perezjuanjose.project2.retrofit;

/**
 * Created by perez.juan.jose on 25/09/2015.
 */
public class Films {


        private String id;
        private String iso6391;
        private String key;
        private String name;
        private String site;
        private int size;
        private String type;

        /**
         * No args constructor for use in serialization
         *
         */
        public Films() {
        }

        /**
         *
         * @param site
         * @param iso6391
         * @param id
         * @param name
         * @param type
         * @param key
         * @param size
         */
        public Films(String id, String iso6391, String key, String name, String site, int size, String type) {
            this.id = id;
            this.iso6391 = iso6391;
            this.key = key;
            this.name = name;
            this.site = site;
            this.size = size;
            this.type = type;
        }

        /**
         *
         * @return
         * The id
         */
        public String getId() {
            return id;
        }

        /**
         *
         * @param id
         * The id
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         *
         * @return
         * The iso6391
         */
        public String getIso6391() {
            return iso6391;
        }

        /**
         *
         * @param iso6391
         * The iso_639_1
         */
        public void setIso6391(String iso6391) {
            this.iso6391 = iso6391;
        }

        /**
         *
         * @return
         * The key
         */
        public String getKey() {
            return key;
        }

        /**
         *
         * @param key
         * The key
         */
        public void setKey(String key) {
            this.key = key;
        }

        /**
         *
         * @return
         * The name
         */
        public String getName() {
            return name;
        }

        /**
         *
         * @param name
         * The name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         *
         * @return
         * The site
         */
        public String getSite() {
            return site;
        }

        /**
         *
         * @param site
         * The site
         */
        public void setSite(String site) {
            this.site = site;
        }

        /**
         *
         * @return
         * The size
         */
        public int getSize() {
            return size;
        }

        /**
         *
         * @param size
         * The size
         */
        public void setSize(int size) {
            this.size = size;
        }

        /**
         *
         * @return
         * The type
         */
        public String getType() {
            return type;
        }

        /**
         *
         * @param type
         * The type
         */
        public void setType(String type) {
            this.type = type;
        }

//    @Override
//    public String toString() {
//        return ToStringBuilder.reflectionToString(this);
//    }

}




