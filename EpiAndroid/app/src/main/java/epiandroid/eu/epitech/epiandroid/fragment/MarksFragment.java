package epiandroid.eu.epitech.epiandroid.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import epiandroid.eu.epitech.epiandroid.CircleTransform;
import epiandroid.eu.epitech.epiandroid.R;
import epiandroid.eu.epitech.epiandroid.adapter.MarksViewAdapter;
import epiandroid.eu.epitech.epiandroid.epitech_service.EpitechService;
import epiandroid.eu.epitech.epiandroid.epitech_service.GsonResponseHandler;
import epiandroid.eu.epitech.epiandroid.model.MarkModel;
import epiandroid.eu.epitech.epiandroid.model.MarksItem;
import me.drakeet.materialdialog.MaterialDialog;


public class MarksFragment extends LoadingFragment implements AdapterView.OnItemClickListener {

    private View view;
    private ListView mMarkListView;
    private Activity mActivity = null;
    private String mCurrentSearch = null;
    private MarksViewAdapter mMarksViewAdapter = null;
    private ArrayList<MarksItem> mMarkItemList = null;

    private GsonResponseHandler<MarkModel> gsonResponseHandler = new GsonResponseHandler<MarkModel>(MarkModel.class) {

        @Override
        public void onSuccess(MarkModel markModel) {
            mMarksViewAdapter = new MarksViewAdapter(getActivity(), R.layout.mark_item, new ArrayList<MarksItem>(Arrays.asList(markModel.getMarksItem())));
            ListView listView = (ListView) view.findViewById(R.id.mark_list);
            listView.setAdapter(mMarksViewAdapter);
            showLoading(false, null);
            showbaseView(true);
        }

        @Override
        public void onFailure(Throwable throwable, JSONObject errorResponse) {
            if (mActivity == null)
                return;

            showLoading(false, null);
            showError(true, getResources().getString(R.string.error_fetching_data));
        }
    };

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
        setHasOptionsMenu(true);
        EpitechService.getRequest("marks", null, gsonResponseHandler);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_mark, menu);
        MenuItem menuItem = menu.findItem(R.id.filter);
        SearchView searchView = (SearchView) menuItem.getActionView();
        setSearchListenerFromActionBar(searchView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_marks, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    private void setSearchListenerFromActionBar(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mCurrentSearch = query;
                if (mMarksViewAdapter != null) {
                    mMarksViewAdapter.filter(mCurrentSearch);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mCurrentSearch = newText;
                if (mMarksViewAdapter != null) {
                    mMarksViewAdapter.filter(mCurrentSearch);
                }
                return false;
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMarkListView = (ListView) view.findViewById(R.id.mark_list);
        mMarkListView.setOnItemClickListener(this);
        setLoadingView(mMarkListView, view.findViewById(R.id.layout_spinner_loading), view.findViewById(R.id.layout_spinner_error));
        view.findViewById(R.id.button_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showError(false, null);
                load();
            }
        });
        load();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
    }

    @Override
    public void load() {
        showbaseView(false);
        showLoading(true, getResources().getString(R.string.loading_data));
        EpitechService.getRequest("marks", null, gsonResponseHandler);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MarksItem marksItem = mMarkItemList.get(position);
        final MaterialDialog materialDialog = new MaterialDialog(mActivity);

        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View markDetail = inflater.inflate(R.layout.mark_detail, null);

        TextView comment = (TextView) markDetail.findViewById(R.id.comment_text_view);
        TextView projectName = (TextView) markDetail.findViewById(R.id.project_name);
        TextView moduleName = (TextView) markDetail.findViewById(R.id.detail_module);
        TextView projectMark = (TextView) markDetail.findViewById(R.id.project_mark);
        TextView markRater = (TextView) markDetail.findViewById(R.id.mark_rater);
        TextView markDate = (TextView) markDetail.findViewById(R.id.mark_date);
        ImageView raterImage = (ImageView) markDetail.findViewById(R.id.rater_image);
        Button button = (Button) markDetail.findViewById(R.id.button_detail_mark);

        button.setTextColor(Color.argb(255, 35, 159, 242));

        String urlImage = "https://cdn.local.epitech.eu/userprofil/profilview/" + marksItem.getRater() + ".jpg";

        Picasso.with(mActivity)
                .load(urlImage)
                .error(R.drawable.person_image_empty)
                .transform(new CircleTransform())
                .into(raterImage);

        moduleName.setText(marksItem.titlemodule);
        projectName.setText(marksItem.getName());
        projectMark.setText(marksItem.getMark());
        comment.setText(marksItem.comment);
        markRater.setText(marksItem.getRater());

        markDate.setText(marksItem.date.split(" ")[0]);

        materialDialog.setContentView(markDetail);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDialog.dismiss();
            }
        });
        materialDialog.show();
    }

}
