package com.quietfall.entity;

import java.util.List;

public class LinkInfo {
    private Integer code;
    private String message;
    private Integer ttl;
    private DataDTO data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTtl() {
        return ttl;
    }

    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class DataDTO {
        private String from;
        private String result;
        private String message;
        private Integer quality;
        private String format;
        private Integer timelength;
        private String acceptFormat;
        private List<String> acceptDescription;
        private List<Integer> acceptQuality;
        private Integer videoCodecid;
        private String seekParam;
        private String seekType;
        private List<DurlDTO> durl;
        private List<SupportFormatsDTO> supportFormats;
        private Object highFormat;
        private Integer lastPlayTime;
        private Integer lastPlayCid;

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Integer getQuality() {
            return quality;
        }

        public void setQuality(Integer quality) {
            this.quality = quality;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public Integer getTimelength() {
            return timelength;
        }

        public void setTimelength(Integer timelength) {
            this.timelength = timelength;
        }

        public String getAcceptFormat() {
            return acceptFormat;
        }

        public void setAcceptFormat(String acceptFormat) {
            this.acceptFormat = acceptFormat;
        }

        public List<String> getAcceptDescription() {
            return acceptDescription;
        }

        public void setAcceptDescription(List<String> acceptDescription) {
            this.acceptDescription = acceptDescription;
        }

        public List<Integer> getAcceptQuality() {
            return acceptQuality;
        }

        public void setAcceptQuality(List<Integer> acceptQuality) {
            this.acceptQuality = acceptQuality;
        }

        public Integer getVideoCodecid() {
            return videoCodecid;
        }

        public void setVideoCodecid(Integer videoCodecid) {
            this.videoCodecid = videoCodecid;
        }

        public String getSeekParam() {
            return seekParam;
        }

        public void setSeekParam(String seekParam) {
            this.seekParam = seekParam;
        }

        public String getSeekType() {
            return seekType;
        }

        public void setSeekType(String seekType) {
            this.seekType = seekType;
        }

        public List<DurlDTO> getDurl() {
            return durl;
        }

        public void setDurl(List<DurlDTO> durl) {
            this.durl = durl;
        }

        public List<SupportFormatsDTO> getSupportFormats() {
            return supportFormats;
        }

        public void setSupportFormats(List<SupportFormatsDTO> supportFormats) {
            this.supportFormats = supportFormats;
        }

        public Object getHighFormat() {
            return highFormat;
        }

        public void setHighFormat(Object highFormat) {
            this.highFormat = highFormat;
        }

        public Integer getLastPlayTime() {
            return lastPlayTime;
        }

        public void setLastPlayTime(Integer lastPlayTime) {
            this.lastPlayTime = lastPlayTime;
        }

        public Integer getLastPlayCid() {
            return lastPlayCid;
        }

        public void setLastPlayCid(Integer lastPlayCid) {
            this.lastPlayCid = lastPlayCid;
        }

        public static class DurlDTO {
            private Integer order;
            private Integer length;
            private Integer size;
            private String ahead;
            private String vhead;
            private String url;
            private List<String> backupUrl;

            public Integer getOrder() {
                return order;
            }

            public void setOrder(Integer order) {
                this.order = order;
            }

            public Integer getLength() {
                return length;
            }

            public void setLength(Integer length) {
                this.length = length;
            }

            public Integer getSize() {
                return size;
            }

            public void setSize(Integer size) {
                this.size = size;
            }

            public String getAhead() {
                return ahead;
            }

            public void setAhead(String ahead) {
                this.ahead = ahead;
            }

            public String getVhead() {
                return vhead;
            }

            public void setVhead(String vhead) {
                this.vhead = vhead;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public List<String> getBackupUrl() {
                return backupUrl;
            }

            public void setBackupUrl(List<String> backupUrl) {
                this.backupUrl = backupUrl;
            }
        }

        public static class SupportFormatsDTO {
            private Integer quality;
            private String format;
            private String newDescription;
            private String displayDesc;
            private String superscript;
            private Object codecs;

            public Integer getQuality() {
                return quality;
            }

            public void setQuality(Integer quality) {
                this.quality = quality;
            }

            public String getFormat() {
                return format;
            }

            public void setFormat(String format) {
                this.format = format;
            }

            public String getNewDescription() {
                return newDescription;
            }

            public void setNewDescription(String newDescription) {
                this.newDescription = newDescription;
            }

            public String getDisplayDesc() {
                return displayDesc;
            }

            public void setDisplayDesc(String displayDesc) {
                this.displayDesc = displayDesc;
            }

            public String getSuperscript() {
                return superscript;
            }

            public void setSuperscript(String superscript) {
                this.superscript = superscript;
            }

            public Object getCodecs() {
                return codecs;
            }

            public void setCodecs(Object codecs) {
                this.codecs = codecs;
            }
        }
    }
}
