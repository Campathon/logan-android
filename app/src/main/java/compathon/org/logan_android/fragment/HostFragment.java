package compathon.org.logan_android.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import compathon.org.logan_android.R;
import compathon.org.logan_android.adapter.PlayingUserAdapter;
import compathon.org.logan_android.model.PlayingUserItem;
import compathon.org.logan_android.service.CrashlyticsService;


/**
 * Created by Andy on 5/10/2018.
 */

public class HostFragment extends Fragment {
    private Context mContext;

    @BindView(R.id.lv_user_list)
    RecyclerView rvUserList;

    private PlayingUserAdapter playingUserAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_host, container, false);
//        View view = inflater.inflate(R.layout.fg_host, null);
        this.mContext = getActivity();
        ButterKnife.bind(this, view);
        rvUserList = view.findViewById(R.id.lv_user_list);

        Bundle bundle = getArguments();
        initViews(bundle);

        return view;
    }

    public void initViews(Bundle bundle) {
        String jsonUsers = "";
        if (bundle != null) jsonUsers = bundle.getString("json_users");

        List<PlayingUserItem> playingUserItemList = new ArrayList<>();
        try {
            playingUserItemList = convertToUserList(jsonUsers);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        playingUserAdapter = new PlayingUserAdapter(mContext, playingUserItemList);
        rvUserList.setLayoutManager(new LinearLayoutManager(mContext));
        rvUserList.setAdapter(playingUserAdapter);
    }

    private List<PlayingUserItem> convertToUserList(String jsonUsers) throws JSONException {
        List<PlayingUserItem> playingUserItemList = new ArrayList<>();

        if (jsonUsers.equals("")) return playingUserItemList;

        JSONArray jsonUserArr = new JSONArray(jsonUsers);
        for (int i = 0; i < jsonUserArr.length(); i++) {
            JSONObject jsonUser = jsonUserArr.getJSONObject(i);
            PlayingUserItem userItem = new PlayingUserItem(jsonUser);
            playingUserItemList.add(userItem);
        }

        return playingUserItemList;
    }

}
