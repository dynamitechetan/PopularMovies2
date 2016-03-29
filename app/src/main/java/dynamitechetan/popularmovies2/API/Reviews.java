

package dynamitechetan.popularmovies2.API;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Reviews {
    @SerializedName("id") private int mMovieId;
    public ArrayList<Review> results;

    public static class Review implements Parcelable {
        @SerializedName("id")      private String mId;
        @SerializedName("author")  private String mAuthor;
        @SerializedName("content") private String mContent;
        @SerializedName("url")     private String mUrl;

        public String getId() {
            return mId;
        }

        public String getAuthor() {
            return mAuthor;
        }

        public String getContent() {
            return mContent;
        }

        public String getUrl() {
            return mUrl;
        }

        private Review(Parcel in) {
            mId = in.readString();
            mAuthor = in.readString();
            mContent = in.readString();
            mUrl = in.readString();
        }

        public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {
            @Override
            public Review createFromParcel(Parcel in) {
                return new Review(in);
            }

            @Override
            public Review[] newArray(int size) {
                return new Review[size];
            }
        };

        @Override
        public void writeToParcel(Parcel out, int flags) {
            out.writeString(mId);
            out.writeString(mAuthor);
            out.writeString(mContent);
            out.writeString(mUrl);
        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}