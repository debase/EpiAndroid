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
public class DashboardFragment extends Fragment {
    private TextView mTextView;
    private MessageModel[] mMessageModel = null;
    private InfoModel mInfoModel = null;
    private LinearLayout mLinearLayout = null;
    private Activity mActivity = null;
    private GsonResponseHandler<MessageModel[]> gsonResponseHandlerMessage = new GsonResponseHandler<MessageModel[]>(MessageModel[].class) {
        @Override
        public void onSuccess(MessageModel[] messageModel) {
            mMessageModel = messageModel;
        }
    };
    private GsonResponseHandler<InfoModel> gsonResponseHandlerInfos = new GsonResponseHandler<InfoModel>(InfoModel.class) {
        @Override
        public void onSuccess(InfoModel infoModel) {
            mInfoModel = infoModel;
            for (InfoModel.HistoryItem hi : infoModel.history) {
                if (mActivity != null) {
                    addHistoryToList(mActivity.getApplicationContext(), hi);
                }
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
        mLinearLayout = (LinearLayout) view.findViewById(R.id.history_linear_layout);
    }
}
