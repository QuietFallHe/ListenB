package com.quietfall.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListInfo {
    @Override
    public String toString() {
        return "ListInfo{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", ttl=" + ttl +
                ", data=" + data +
                '}';
    }

    private Long code;
    private String message;
    private Long ttl;
    private DataDTO data;

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class DataDTO {
        private InfoDTO info;
        private List<MediasDTO> medias;
        private Boolean hasMore;
        private Long ttl;

        public InfoDTO getInfo() {
            return info;
        }

        public void setInfo(InfoDTO info) {
            this.info = info;
        }

        public List<MediasDTO> getMedias() {
            return medias;
        }

        public void setMedias(List<MediasDTO> medias) {
            this.medias = medias;
        }

        public Boolean getHasMore() {
            return hasMore;
        }

        public void setHasMore(Boolean hasMore) {
            this.hasMore = hasMore;
        }

        public Long getTtl() {
            return ttl;
        }

        public void setTtl(Long ttl) {
            this.ttl = ttl;
        }

        public static class InfoDTO {
            private Long id;
            private Long fid;
            private Long mid;
            private Long attr;
            private String title;
            private String cover;
            private UpperDTO upper;
            @SerializedName("cover_type")
            private Long coverType;
            private CntInfoDTO cntInfo;
            private Long type;
            private String intro;
            private Long ctime;
            private Long mtime;
            private Long state;
            private Long favState;
            private Long likeState;
            @SerializedName("media_count")
            private Long mediaCount;

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public Long getFid() {
                return fid;
            }

            public void setFid(Long fid) {
                this.fid = fid;
            }

            public Long getMid() {
                return mid;
            }

            public void setMid(Long mid) {
                this.mid = mid;
            }

            public Long getAttr() {
                return attr;
            }

            public void setAttr(Long attr) {
                this.attr = attr;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public UpperDTO getUpper() {
                return upper;
            }

            public void setUpper(UpperDTO upper) {
                this.upper = upper;
            }

            public Long getCoverType() {
                return coverType;
            }

            public void setCoverType(Long coverType) {
                this.coverType = coverType;
            }

            public CntInfoDTO getCntInfo() {
                return cntInfo;
            }

            public void setCntInfo(CntInfoDTO cntInfo) {
                this.cntInfo = cntInfo;
            }

            public Long getType() {
                return type;
            }

            public void setType(Long type) {
                this.type = type;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public Long getCtime() {
                return ctime;
            }

            public void setCtime(Long ctime) {
                this.ctime = ctime;
            }

            public Long getMtime() {
                return mtime;
            }

            public void setMtime(Long mtime) {
                this.mtime = mtime;
            }

            public Long getState() {
                return state;
            }

            public void setState(Long state) {
                this.state = state;
            }

            public Long getFavState() {
                return favState;
            }

            public void setFavState(Long favState) {
                this.favState = favState;
            }

            public Long getLikeState() {
                return likeState;
            }

            public void setLikeState(Long likeState) {
                this.likeState = likeState;
            }

            public Long getMediaCount() {
                return mediaCount;
            }

            public void setMediaCount(Long mediaCount) {
                this.mediaCount = mediaCount;
            }

            public static class UpperDTO {
                private Long mid;
                private String name;
                private String face;
                private Boolean followed;
                private Long vipType;
                private Long vipStatue;

                public Long getMid() {
                    return mid;
                }

                public void setMid(Long mid) {
                    this.mid = mid;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getFace() {
                    return face;
                }

                public void setFace(String face) {
                    this.face = face;
                }

                public Boolean getFollowed() {
                    return followed;
                }

                public void setFollowed(Boolean followed) {
                    this.followed = followed;
                }

                public Long getVipType() {
                    return vipType;
                }

                public void setVipType(Long vipType) {
                    this.vipType = vipType;
                }

                public Long getVipStatue() {
                    return vipStatue;
                }

                public void setVipStatue(Long vipStatue) {
                    this.vipStatue = vipStatue;
                }
            }

            public static class CntInfoDTO {
                private Long collect;
                private Long play;
                private Long thumbUp;
                private Long share;

                public Long getCollect() {
                    return collect;
                }

                public void setCollect(Long collect) {
                    this.collect = collect;
                }

                public Long getPlay() {
                    return play;
                }

                public void setPlay(Long play) {
                    this.play = play;
                }

                public Long getThumbUp() {
                    return thumbUp;
                }

                public void setThumbUp(Long thumbUp) {
                    this.thumbUp = thumbUp;
                }

                public Long getShare() {
                    return share;
                }

                public void setShare(Long share) {
                    this.share = share;
                }
            }
        }

        public static class MediasDTO {
            private Long id;
            private Long type;
            private String title;
            private String cover;
            private String intro;
            private Long page;
            private Long duration;
            private UpperDTO upper;
            private Long attr;
            private CntInfoDTO cntInfo;
            private String link;
            private Long ctime;
            private Long pubtime;
            private Long favTime;
            private String bvId;
            private String bvid;
            private Object season;
            private Object ogv;
            private UgcDTO ugc;

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public Long getType() {
                return type;
            }

            public void setType(Long type) {
                this.type = type;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public Long getPage() {
                return page;
            }

            public void setPage(Long page) {
                this.page = page;
            }

            public Long getDuration() {
                return duration;
            }

            public void setDuration(Long duration) {
                this.duration = duration;
            }

            public UpperDTO getUpper() {
                return upper;
            }

            public void setUpper(UpperDTO upper) {
                this.upper = upper;
            }

            public Long getAttr() {
                return attr;
            }

            public void setAttr(Long attr) {
                this.attr = attr;
            }

            public CntInfoDTO getCntInfo() {
                return cntInfo;
            }

            public void setCntInfo(CntInfoDTO cntInfo) {
                this.cntInfo = cntInfo;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public Long getCtime() {
                return ctime;
            }

            public void setCtime(Long ctime) {
                this.ctime = ctime;
            }

            public Long getPubtime() {
                return pubtime;
            }

            public void setPubtime(Long pubtime) {
                this.pubtime = pubtime;
            }

            public Long getFavTime() {
                return favTime;
            }

            public void setFavTime(Long favTime) {
                this.favTime = favTime;
            }

            public String getBvId() {
                return bvId;
            }

            public void setBvId(String bvId) {
                this.bvId = bvId;
            }

            public String getBvid() {
                return bvid;
            }

            public void setBvid(String bvid) {
                this.bvid = bvid;
            }

            public Object getSeason() {
                return season;
            }

            public void setSeason(Object season) {
                this.season = season;
            }

            public Object getOgv() {
                return ogv;
            }

            public void setOgv(Object ogv) {
                this.ogv = ogv;
            }

            public UgcDTO getUgc() {
                return ugc;
            }

            public void setUgc(UgcDTO ugc) {
                this.ugc = ugc;
            }

            public static class UpperDTO {
                private Long mid;
                private String name;
                private String face;

                public Long getMid() {
                    return mid;
                }

                public void setMid(Long mid) {
                    this.mid = mid;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getFace() {
                    return face;
                }

                public void setFace(String face) {
                    this.face = face;
                }
            }

            public static class CntInfoDTO {
                private Long collect;
                private Long play;
                private Long danmaku;
                private Long vt;
                private Long playSwitch;
                private Long reply;
                private String viewText1;

                public Long getCollect() {
                    return collect;
                }

                public void setCollect(Long collect) {
                    this.collect = collect;
                }

                public Long getPlay() {
                    return play;
                }

                public void setPlay(Long play) {
                    this.play = play;
                }

                public Long getDanmaku() {
                    return danmaku;
                }

                public void setDanmaku(Long danmaku) {
                    this.danmaku = danmaku;
                }

                public Long getVt() {
                    return vt;
                }

                public void setVt(Long vt) {
                    this.vt = vt;
                }

                public Long getPlaySwitch() {
                    return playSwitch;
                }

                public void setPlaySwitch(Long playSwitch) {
                    this.playSwitch = playSwitch;
                }

                public Long getReply() {
                    return reply;
                }

                public void setReply(Long reply) {
                    this.reply = reply;
                }

                public String getViewText1() {
                    return viewText1;
                }

                public void setViewText1(String viewText1) {
                    this.viewText1 = viewText1;
                }
            }

            public static class UgcDTO {
                @SerializedName("first_cid")
                private Long firstCid;

                public Long getFirstCid() {
                    return firstCid;
                }

                public void setFirstCid(Long firstCid) {
                    this.firstCid = firstCid;
                }
            }
        }
    }
}
