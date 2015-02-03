package epiandroid.eu.epitech.epiandroid.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Comparator;

import epiandroid.eu.epitech.epiandroid.R;
import epiandroid.eu.epitech.epiandroid.adapter.ModuleAdapter;
import epiandroid.eu.epitech.epiandroid.epitech_service.EpitechService;
import epiandroid.eu.epitech.epiandroid.epitech_service.GsonResponseHandler;
import epiandroid.eu.epitech.epiandroid.model.ModuleItem;
import epiandroid.eu.epitech.epiandroid.model.ModulesModel;


public class ModulesFragment extends Fragment {

    private ListView modulesList;
    private ModuleAdapter listAdapter;

    private GsonResponseHandler<ModulesModel> gsonResponseHandler = new GsonResponseHandler<ModulesModel>(ModulesModel.class) {
        @Override
        public void onSuccess(ModulesModel modulesModel) {

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
        }

        @Override
        public void onFailure(Throwable throwable, JSONObject errorResponse) {

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
        }
    };

    public ModulesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EpitechService.getRequest("modules", null, gsonResponseHandler);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View V = inflater.inflate(R.layout.fragment_modules, container, false);

        this.modulesList  = (ListView) V.findViewById(R.id.modulesList);

        return V;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
