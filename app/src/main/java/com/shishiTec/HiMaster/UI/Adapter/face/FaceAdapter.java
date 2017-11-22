package com.shishiTec.HiMaster.UI.Adapter.face;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shishiTec.HiMaster.Model.bean.EmojiBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.Utils.FileUtil;

import java.util.List;

public class FaceAdapter extends AppBaseAdapter<EmojiBean>{

    public FaceAdapter(Context context, List<EmojiBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EmojiBean emoji=list.get(position);
        ViewHolder viewHolder=null;
        if(convertView == null) {
            viewHolder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.item_face,null);
            viewHolder.iv_face=(ImageView)convertView.findViewById(R.id.item_iv_face);
            convertView.setTag(viewHolder);
        } else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.iv_face.setImageBitmap(FileUtil.getImageFromAssetsFile(emoji.getFileName(),true));
        viewHolder.iv_face.setTag(emoji.getText());
        return convertView;
    }

    class ViewHolder {

        public ImageView iv_face;
    }
}
