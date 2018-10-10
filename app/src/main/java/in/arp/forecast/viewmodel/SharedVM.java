package in.arp.forecast.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class SharedVM extends ViewModel {

    private final MutableLiveData<String> selected = new MutableLiveData<String>();

    public void select(String location) {
        selected.setValue(location);
    }

    public LiveData<String> getSelected() {
        return selected;
    }
}
