package com.a3xh1.basecore.customview.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a3xh1.basecore.customview.WheelView;
import com.a3xh1.basecore.databinding.DialogAddressSelectorBinding;
import com.a3xh1.basecore.pojo.BasicAddress;
import com.a3xh1.basecore.utils.Const;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Author: GIndoc on 2018/2/9 上午10:39
 * email : 735506583@qq.com
 * FOR   :
 */
public class AddressSelectorDialog extends BaseDialogFragment {
    private DialogAddressSelectorBinding mBinding;
    private List<BasicAddress> province;
    private List<BasicAddress> city;
    private List<BasicAddress> area;
    private int areaId;
    private int provinceId;
    private int cityId;
    private int areaSelectPosition;


    @Inject
    public AddressSelectorDialog() {
    }

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DialogAddressSelectorBinding.inflate(inflater, container, false);
        mBinding.setDialog(this);
        initListener();
        return mBinding.getRoot();
    }

    @Override
    public void initData() {
        List<BasicAddress> list = getArguments().getParcelableArrayList(Const.KEY.ADDRESS);
        if (list == null) {
            return;
        }
        province = list;
        city = province.get(0).getModelList();
        area = city.get(0).getModelList();
        mBinding.proviceWheel.refreshData(getNames(province));
        mBinding.proviceWheel.setDefault(0);
        mBinding.cityWheel.setData(getNames(city));
        mBinding.cityWheel.setDefault(0);
        mBinding.areaWheel.setData(getNames(area));
        mBinding.areaWheel.setDefault(0);
        provinceId = province.get(0).getId();
        cityId = city.get(0).getId();
        areaId = area.get(0).getId();
    }

    public void cancel() {
        dismiss();
    }

    public void choose() {
        if (addressSelectedListener != null) {
            addressSelectedListener.onAddressSelected(province.get(mBinding.proviceWheel.getSelected()).getAddressname(),
                    city.get(mBinding.cityWheel.getSelected()).getAddressname(),
                    area.get(areaSelectPosition).getAddressname(),
                    provinceId, cityId, areaId);
        }
    }

    private void initListener() {
        mBinding.proviceWheel.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                BasicAddress address = province.get(id);
                provinceId = address.getId();
                city = address.getModelList();
                area = city.get(0).getModelList();
                cityId = city.get(0).getId();
                areaId = area.get(0).getId();
                mBinding.cityWheel.post(new Runnable() {
                    @Override
                    public void run() {
                        mBinding.cityWheel.refreshData(getNames(city));
                        mBinding.areaWheel.refreshData(getNames(area));
                        mBinding.cityWheel.setDefault(0);
                        mBinding.areaWheel.setDefault(0);
                    }
                });
            }

            @Override
            public void selecting(int id, String text) {
            }
        });

        mBinding.cityWheel.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                area = city.get(id).getModelList();
                cityId = city.get(id).getId();
                areaId = area.get(0).getId();
                mBinding.areaWheel.post(new Runnable() {
                    @Override
                    public void run() {
                        mBinding.areaWheel.refreshData(getNames(area));
                        mBinding.areaWheel.setDefault(0);
                    }
                });
            }

            @Override
            public void selecting(int id, String text) {

            }
        });
        mBinding.areaWheel.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                areaId = area.get(id).getId();
                areaSelectPosition = id;
            }

            @Override
            public void selecting(int id, String text) {
            }
        });

    }

    private ArrayList<String> getNames(List<BasicAddress> locationList) {
        ArrayList<String> nameList = new ArrayList<>();
        for (int i = 0; i < locationList.size(); i++) {
            nameList.add(locationList.get(i).getAddressname());
        }
        return nameList;
    }

    public interface AddressSelectedListener {
        void onAddressSelected(String province, String city, String area, int provinceId, int cityId, int areaId);
    }

    public AddressSelectedListener addressSelectedListener;

    public void setAddressSelectedListener(AddressSelectedListener addressSelectedListener) {
        this.addressSelectedListener = addressSelectedListener;
    }

}
