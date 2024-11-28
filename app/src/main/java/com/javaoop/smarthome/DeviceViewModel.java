package com.javaoop.smarthome;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DeviceViewModel extends ViewModel {
    private final MutableLiveData<List<Device>> devices = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<Device>> getDevices() {
        return devices;
    }

    public MutableLiveData<List<Device>> getMutableDevices() {
        return devices;
    }

    public void addDevice(Device device) {
        List<Device> currentDevices = new ArrayList<>(devices.getValue());
        currentDevices.add(device);
        devices.setValue(currentDevices);
    }

    public void removeDevice(Device device) {
        List<Device> currentDevices = new ArrayList<>(devices.getValue());
        currentDevices.remove(device);
        devices.setValue(currentDevices);
    }

    public void setDevices(List<Device> updatedDevices) {
        devices.setValue(updatedDevices);
    }

    public void readJson(Context context) {
        try {
            // Đọc file từ res/raw
            InputStream inputStream = context.getResources().openRawResource(R.raw.sample);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();

            // Chuyển đổi dữ liệu thành chuỗi JSON
            String json = new String(buffer, StandardCharsets.UTF_8);

            // Parse chuỗi JSON
            JSONObject jsonObject = new JSONObject(json);

            // Lấy dữ liệu từ các trường trong JSON
            String status = jsonObject.getString("status");
            String message = jsonObject.getString("message");

            // Lấy mảng "data"
            JSONArray dataArray = jsonObject.getJSONArray("data");

            // Lặp qua từng phần tử trong mảng
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject device = dataArray.getJSONObject(i);
                String id = device.getString("id");
                int port = device.getInt("port");
                String deviceType = device.getString("deviceType");
                String deviceName = device.getString("deviceName");
                String deviceData = device.getString("deviceData"); // Có thể là boolean hoặc số

                // In thông tin ra log (hoặc lưu vào biến tùy ý)
                Device device1 = new Device(String.valueOf(i+1), String.valueOf(port), id, deviceData, deviceType, deviceName );
                addDevice(device1);
                //addevice
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
