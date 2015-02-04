package epiandroid.eu.epitech.epiandroid.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.Comparator;

import epiandroid.eu.epitech.epiandroid.R;
import epiandroid.eu.epitech.epiandroid.adapter.ModuleAdapter;
import epiandroid.eu.epitech.epiandroid.epitech_service.EpitechService;
import epiandroid.eu.epitech.epiandroid.epitech_service.GsonResponseHandler;
import epiandroid.eu.epitech.epiandroid.model.ModuleItem;
import epiandroid.eu.epitech.epiandroid.model.ModulesModel;


public class ModulesFragment extends LoadingFragment {

    private ListView modulesList;
    private ModuleAdapter listAdapter;
    private Activity mActivity;

    private GsonResponseHandler<ModulesModel> gsonResponseHandler = new GsonResponseHandler<ModulesModel>(ModulesModel.class) {
        @Override
        public void onSuccess(ModulesModel modulesModel) {

            if(mActivity == null)
                return;

            ModuleItem[] items = modulesModel.getModuleItem();

            Arrays.sort(items, new Comparator<ModuleItem>() {
                @Override
                public int compare(ModuleItem lhs, ModuleItem rhs) {
                    Integer yearA = Integer.parseInt(lhs.getScolaryear());
                    Integer yearB = Integer.parseInt(rhs.getScolaryear());

                    return yearB.compareTo(yearA);
                }
            });

            listAdapter = new ModuleAdapter(getActivity(), R.layout.adapter_module, items);
            modulesList.setAdapter(listAdapter);
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

    public ModulesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View V = inflater.inflate(R.layout.fragment_modules, container, false);

        this.modulesList  = (ListView) V.findViewById(R.id.modulesList);

        setLoadingView(modulesList, V.findViewById(R.id.layout_spinner_loading), V.findViewById(R.id.layout_spinner_error));

        V.findViewById(R.id.button_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showError(false, null);
                load();
            }
        });
        load();

        return V;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void load() {
        showbaseView(false);
        showLoading(true, getResources().getString(R.string.loading_data));
        EpitechService.getRequest("modules", null, gsonResponseHandler);
    }
}