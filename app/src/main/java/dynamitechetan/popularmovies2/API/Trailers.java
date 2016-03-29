package dynamitechetan.popularmovies2.API;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class Trailers {

    @SerializedName("id") private int mMovieId;
    public ArrayList<Trailer> results;

    public static class Trailer implements Parcelable {

        @SerializedName("id")        private String mId;
        @SerializedName("iso_639_1") private String mIso639_1;
        @SerializedName("key")       private String mKey;
        @SerializedName("name")      private String mName;
        @SerializedName("site")      private String mSite;
        @SerializedName("size")      private int mSize;
        @SerializedName("type")      private String mType;

        private final String YOUTUBE_URL = "https://www.youtube.com/watch?v=";

        public String getId() {
            return mId;
        }

        public String getIso639_1() {
            return mIso639_1;
        }

        public String getKey() {
            return mKey;
        }

        public String getName() {
            return mName;
        }

        public String getSite() {
            return mSite;
        }

        public int getSize() {
            return mSize;
        }

        public String getType() {
            return mType;
        }

        public String createYoutubeLink() {
            return YOUTUBE_URL + mKey;
        }

        private Trailer(Parcel in) {
            mId = in.readString();
            mIso639_1 = in.readString();
            mKey = in.readString();
            mName = in.readString();
            mSite = in.readString();
            mSize = in.readInt();
            mType = in.readString();
        }

        public static final Parcelable.Creator<Trailer> CREATOR = new Parcelable.Creator<Trailer>() {
            @Override
            public Trailer createFromParcel(Parcel in) {
                return new Trailer(in);
            }

            @Override
            public Trailer[] newArray(int size) {
                return new Trailer[size];
            }
        };

        @Override
        public void writeToParcel(Parcel out, int flags) {
            out.writeString(mId);
            out.writeString(mIso639_1);
            out.writeString(mKey);
            out.writeString(mName);
            out.writeString(mSite);
            out.writeInt(mSize);
            out.writeString(mType);
        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}