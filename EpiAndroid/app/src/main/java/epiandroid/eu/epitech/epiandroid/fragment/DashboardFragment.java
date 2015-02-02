package epiandroid.eu.epitech.epiandroid.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.squareup.picasso.Picasso;

import epiandroid.eu.epitech.epiandroid.CircleTransform;
import epiandroid.eu.epitech.epiandroid.R;
import epiandroid.eu.epitech.epiandroid.epitech_service.EpitechService;
import epiandroid.eu.epitech.epiandroid.epitech_service.GsonResponseHandler;
import epiandroid.eu.epitech.epiandroid.model.InfoModel;
import epiandroid.eu.epitech.epiandroid.model.MessageModel;
import epiandroid.eu.epitech.epiandroid.utils.Utils;

/**
 * Created by debas on 28/01/15.
 */
public class DashboardFragment extends Fragment implements ObservableScrollViewCallbacks {
    private LinearLayout mLinearLayout = null;
    private Activity mActivity = null;
    private ImageView mImageView = null;
    private float mParallaxImageHeight = 0;
    private ImageView mUserImageView = null;
    private TextView mUserTitle, mUserMail, mUserPromo, mUserSemester, mUserCurrentCredit, mUserObjectifCredit, mUserNetSoul;
    private GsonResponseHandler<MessageModel[]> gsonResponseHandlerMessage = new GsonResponseHandler<MessageModel[]>(MessageModel[].class) {
        @Override
        public void onSuccess(MessageModel[] messageModel) {
        }
    };
    private GsonResponseHandler<InfoModel> gsonResponseHandlerInfos = new GsonResponseHandler<InfoModel>(InfoModel.class) {
        @Override
        public void onSuccess(InfoModel infoModel) {
            for (int i = 0; i < infoModel.history.length && i < 4; i++) {
                InfoModel.HistoryItem hi = infoModel.history[i];
                if (mActivity != null) {
                    addHistoryToList(mActivity.getApplicationContext(), hi);
                }
            }
            if (mActivity != null) {
                String urlProfilPicture = "https://cdn.local.epitech.eu/userprofil/profilview/" + infoModel.infos.login + ".jpg";
                Picasso.with(mActivity)
                        .load(urlProfilPicture)
                        .transform(new CircleTransform())
                        .error(R.drawable.person_image_empty)
                        .into(mUserImageView);
                mUserTitle.setText(infoModel.infos.title);
                mUserMail.setText(infoModel.infos.mail);
                mUserPromo.setText(infoModel.infos.promo);
                mUserSemester.setText(infoModel.infoCurrent.user_semester);
                mUserCurrentCredit.setText(infoModel.infoCurrent.current_credit);
                mUserObjectifCredit.setText(infoModel.infoCurrent.objectif_credit);
                mUserNetSoul.setText(infoModel.infoCurrent.active_log);
            }
        }
    };

    private void addHistoryToList(Context applicationContext, InfoModel.HistoryItem history) {
        View v = LayoutInflater.from(applicationContext).inflate(R.layout.history_card, mLinearLayout, false);

        TextView title = (TextView) v.findViewById(R.id.title);
        TextView  content = (TextView) v.findViewById(R.id.content);
        TextView  userName = (TextView) v.findViewById(R.id.user_name);
        TextView historyDate = (TextView) v.findViewById(R.id.history_date);
        ImageView imageView = (ImageView) v.findViewById(R.id.history_image);

        title.setText(Html.fromHtml(history.title));
        content.setText(Html.fromHtml(history.content));
        userName.setText(history.user.title);
        historyDate.setText(history.historyDate);

        if (history.user.picture != null) {
            imageView.setVisibility(View.VISIBLE);
            Picasso.with(applicationContext)
                    .load(history.user.picture)
                    .transform(new CircleTransform())
                    .error(R.drawable.person_image_empty)
                    .into(imageView);
        } else {
            imageView.setVisibility(View.GONE);
        }
        mLinearLayout.addView(v);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (savedInstanceState == null) {
            Utils.makeText(getActivity(), "OnCreate");
            EpitechService.getRequest("infos", null, gsonResponseHandlerInfos);
            EpitechService.getRequest("messages", null, gsonResponseHandlerMessage);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dashboard, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ObservableScrollView mObservableScrollView = (ObservableScrollView) view.findViewById(R.id.scroll_view_dashboard);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.history_linear_layout);
        mParallaxImageHeight = getResources().getDimension(R.dimen.header_view_dashboard_height);
        mImageView = (ImageView) view.findViewById(R.id.epitech_image);
        mUserImageView = (ImageView) view.findViewById(R.id.header_imageview_dashboard);

        mUserTitle = (TextView) view.findViewById(R.id.title_user);
        mUserMail = (TextView) view.findViewById(R.id.email_user);
        mUserPromo = (TextView) view.findViewById(R.id.student_year);
        mUserSemester = (TextView) view.findViewById(R.id.student_semester);
        mUserCurrentCredit = (TextView) view.findViewById(R.id.current_credit);
        mUserObjectifCredit = (TextView) view.findViewById(R.id.objectif_credit);
        mUserNetSoul = (TextView) view.findViewById(R.id.current_netsoul);

        mObservableScrollView.setScrollViewCallbacks(this);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        mImageView.setTranslationY(scrollY / 2);
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }
}
