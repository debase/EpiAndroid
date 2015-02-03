package epiandroid.eu.epitech.epiandroid.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.apache.http.Header;

import epiandroid.eu.epitech.epiandroid.R;
import epiandroid.eu.epitech.epiandroid.adapter.ModuleAdapter;
import epiandroid.eu.epitech.epiandroid.epitech_service.EpitechService;
import epiandroid.eu.epitech.epiandroid.epitech_service.GsonResponseHandler;
import epiandroid.eu.epitech.epiandroid.model.ModulesModel;


public class ModulesFragment extends Fragment {

    private ListView modulesList;
    private ModuleAdapter listAdapter;

    private GsonResponseHandler<ModulesModel> gsonResponseHandler = new GsonResponseHandler<ModulesModel>(ModulesModel.class) {
        @Override
        public void onSuccess(ModulesModel modulesModel) {
            Log.i("Modules", modulesModel.getModuleItem().toString());
            listAdapter = new ModuleAdapter(getActivity(), R.layout.fragment_modules, modulesModel.getModuleItem());
            modulesList.setAdapter(listAdapter);
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
