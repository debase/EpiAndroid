package epiandroid.eu.epitech.epiandroid.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by remihillairet on 03/02/15.
 */
public class ModulesModel {

    @SerializedName("modules")
    private ModuleItem[] modules;

    public void setModuleItem(ModuleItem[] modules) {
        this.modules = modules;
    }

    public ModuleItem[] getModuleItem() {
        return this.modules;
    }
}
