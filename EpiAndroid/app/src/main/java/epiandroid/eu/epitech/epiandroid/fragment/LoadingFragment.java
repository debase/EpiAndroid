package epiandroid.eu.epitech.epiandroid.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import epiandroid.eu.epitech.epiandroid.R;

/**
 * Created by debas on 02/02/15.
 */
public abstract class LoadingFragment extends Fragment {
    View mBaseView = null, mLoadingView = null, mErrorView = null;

    public void setLoadingView(View baseView, View loadingView, View errorView) {
        mBaseView = baseView;
        mLoadingView = loadingView;
        mErrorView = errorView;
    }

    public void showLoading(boolean visible, String message) {
        if (visible) {
            mLoadingView.setVisibility(View.VISIBLE);
            TextView textView = (TextView) mLoadingView.findViewById(R.id.text_view_spinner_loading);
            if (textView != null) {
                textView.setText(message);
            }
        } else {
            mLoadingView.setVisibility(View.GONE);
        }
    }

    public void showError(boolean visible, String message) {
        if (visible) {
            mErrorView.setVisibility(View.VISIBLE);
            TextView textView = (TextView) mErrorView.findViewById(R.id.text_view_spinner_error);
            if (textView != null) {
                textView.setText(message);
            }
        } else {
            mErrorView.setVisibility(View.GONE);
        }
    }

    public void showbaseView(boolean visible) {
        if (visible) {
            mBaseView.setVisibility(View.VISIBLE);
        } else {
            mBaseView.setVisibility(View.GONE);
        }
    }

    public abstract void load();
}
