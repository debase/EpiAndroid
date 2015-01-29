package epiandroid.eu.epitech.epiandroid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import epiandroid.eu.epitech.epiandroid.R;
import epiandroid.eu.epitech.epiandroid.epitech_service.EpitechService;
import epiandroid.eu.epitech.epiandroid.epitech_service.GsonResponseHandler;
import epiandroid.eu.epitech.epiandroid.model.MessageModel;
import epiandroid.eu.epitech.epiandroid.utils.Utils;

/**
 * Created by debas on 28/01/15.
 */
public class DashboardFragment extends Fragment {
    private TextView mTextView;
    private MessageModel[] mMessageModel = null;
    private GsonResponseHandler<MessageModel[]> gsonResponseHandlerMessage = new GsonResponseHandler<MessageModel[]>(MessageModel[].class) {
        @Override
        public void onSuccess(MessageModel[] messageModel) {
            mMessageModel = messageModel;
            for (MessageModel m : messageModel) {
                mTextView.append(Html.fromHtml(m.title));
                mTextView.append("\n");
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (savedInstanceState == null) {
            Utils.makeText(getActivity(), "OnCreate");
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
        mTextView = (TextView) view.findViewById(R.id.result_textview);
        if (mMessageModel != null) {
            for (MessageModel m : mMessageModel) {
                mTextView.append(Html.fromHtml(m.title));
                mTextView.append("\n");
            }
        }
    }
}
