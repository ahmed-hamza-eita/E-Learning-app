package com.hamza.e_learningapp.ui.chat.chatbot;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamza.e_learningapp.BaseFragment;
import com.hamza.e_learningapp.R;
import com.hamza.e_learningapp.adapters.AdapterChatBot;
import com.hamza.e_learningapp.databinding.FragmentChatbotBinding;
import com.hamza.e_learningapp.models.ChatBotModel;
import com.hamza.e_learningapp.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


@AndroidEntryPoint
public class ChatBotFragment extends BaseFragment {

    private FragmentChatbotBinding binding;
    private AdapterChatBot adapterChatBot;
    List<ChatBotModel> list = new ArrayList<ChatBotModel>();
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatbotBinding.inflate(inflater, container, false);
        adapterChatBot = new AdapterChatBot();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        actions();
    }

    private void actions() {
        adapterChatBot.setList(list);
        binding.recyclerView.setAdapter(adapterChatBot);
        binding.sendBtn.setOnClickListener(view -> {
            String message = binding.messageText.getText().toString().trim();
            addToChat(message, ChatBotModel.SEND_BY_ME);
            binding.messageText.setText("");
            callApi(message);
        });

    }

    void addToChat(String message, String sendBy) {
        mFragmentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                list.add(new ChatBotModel(message, sendBy));
                adapterChatBot.notifyDataSetChanged();
                binding.recyclerView.smoothScrollToPosition(adapterChatBot.getItemCount());

            }
        });
    }

    void addResponse(String response) {
        list.remove(list.size() - 1);
        addToChat(response, ChatBotModel.SEND_BY_BOT);
    }

    private void callApi(String message) {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model", "text-davinci-003");
            jsonBody.put("prompt", message);
            jsonBody.put("max_tokens", 40000);
            jsonBody.put("temperature", 0);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        RequestBody body = RequestBody.create(jsonBody.toString(), JSON);
        Request request = new Request.Builder()
                .url(Const.API_URL)
                .header("Authorization", "Bearer "+Const.API_KEY)
                //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"

                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addResponse(e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    JSONObject jsonObject  ;
                    try {

                        jsonObject = new JSONObject(response.body(). string());
                       final JSONArray jsonArray = jsonObject.getJSONArray("choices");
                        String result = jsonArray.getJSONObject(0).getString("text");
                        addResponse(result.trim());
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    addResponse(response.body().toString());
                }
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}