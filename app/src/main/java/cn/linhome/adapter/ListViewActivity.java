package cn.linhome.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import cn.linhome.adapter.model.DataModel;
import cn.linhome.lib.adapter.select.FSelectSimpleAdapter;
import cn.linhome.lib.selectionmanager.FSelectManager;

public class ListViewActivity extends Activity
{
    public static final String TAG = ListViewActivity.class.getSimpleName();

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_listview);
        mListView = findViewById(R.id.listview);
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DataModel model = new DataModel();
                model.name = String.valueOf(mAdapter.getDataHolder().size());

                mAdapter.getDataHolder().appendData(model);
            }
        });

        mListView.setAdapter(mAdapter);
        mAdapter.getDataHolder().setData(DataModel.get(5));

        mAdapter.getSelectManager().setMode(FSelectManager.Mode.MULTI);
        mAdapter.getSelectManager().addCallback(new FSelectManager.Callback<DataModel>()
        {
            @Override
            public void onNormal(DataModel item)
            {
                updateSelectedInfo();
            }

            @Override
            public void onSelected(DataModel item)
            {
                updateSelectedInfo();
            }
        });
    }

    private void updateSelectedInfo()
    {
        final TextView textView = findViewById(R.id.tv_select);

        if (mAdapter.getSelectManager().isSingleMode())
        {
            textView.setText(String.valueOf(mAdapter.getSelectManager().getSelectedItem()));
        } else
        {
            textView.setText(String.valueOf(mAdapter.getSelectManager().getSelectedItems()));
        }
    }

    private FSelectSimpleAdapter<DataModel> mAdapter = new FSelectSimpleAdapter<DataModel>(this)
    {
        @Override
        public int getLayoutId(int position, View convertView, ViewGroup parent)
        {
            return R.layout.item_list;
        }

        @Override
        public void onBindData(int position, View convertView, ViewGroup parent, final DataModel model)
        {
            TextView textView = get(R.id.textview, convertView);
            textView.setText(model.name);

            if (model.isSelected())
            {
                textView.setTextColor(Color.RED);
            } else
            {
                textView.setTextColor(Color.BLACK);
            }

            convertView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    getSelectManager().performClick(model);
                }
            });
            convertView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    getDataHolder().removeData(model);
                    return false;
                }
            });
        }
    };
}
