package com.example.fundament.presentation.sample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fundament.R
import com.example.fundament.base.Status
import com.example.fundament.entities.Sample
import com.example.fundament.extensions.alert
import com.example.fundament.extensions.toast
import kotlinx.android.synthetic.main.activity_sample.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SampleActivity : AppCompatActivity(), SampleAdapter.MyClickListener {

    private lateinit var viewModel: SampleViewModel
    private var sampleAdapter = SampleAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)
        viewModel = getViewModel()
        viewModel.statusMutableLiveData.observe(this, Observer {
            when(it){
                Status.SHOW_LOADING -> {
                    mRecyclerView.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }
                Status.HIDE_LOADING -> {
                    mRecyclerView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                }
            }
        })
        viewModel.messageLiveData.observe(this, Observer {
            toast(it)
        })
        sampleAdapter.setListener(this)

        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = sampleAdapter

        viewModel.getSamples()

        viewModel.sampleListLiveData.observe(this, Observer {
            sampleAdapter.setDataSet(it)
        })

        fab.setOnClickListener {
            val sampleObject = Sample("My title ${sampleAdapter.getDataSetSize() + 1}", "My description ${sampleAdapter.getDataSetSize() + 1}")
            viewModel.postSample(sampleObject)
            sampleAdapter.addItem(sampleObject)
        }
    }

    override fun onClick(item: Sample) {
        alert(item.title.orEmpty(), item.description.orEmpty())
    }
}
