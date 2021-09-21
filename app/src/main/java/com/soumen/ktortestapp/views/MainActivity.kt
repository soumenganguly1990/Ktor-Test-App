package com.soumen.ktortestapp.views

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.soumen.ktortestapp.R
import com.soumen.ktortestapp.adapters.CommonRecyclerViewAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.soumen.ktortestapp.apihandler.ApiStatus
import com.soumen.ktortestapp.databinding.ActivityMainBinding
import com.soumen.ktortestapp.entities.UserEntity
import com.soumen.ktortestapp.utils.Utils
import com.soumen.ktortestapp.utils.Utils.Companion.gone
import com.soumen.ktortestapp.utils.Utils.Companion.loadImage
import com.soumen.ktortestapp.utils.Utils.Companion.setUpVertical
import com.soumen.ktortestapp.utils.Utils.Companion.updateRecyclerView
import com.soumen.ktortestapp.utils.Utils.Companion.visible
import com.soumen.ktortestapp.viewmodels.UserViewModel

class MainActivity : AppCompatActivity() {

    private val allUsers: MutableList<UserEntity> by lazy { mutableListOf<UserEntity>() }
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private var pageToLoad: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityMainBinding.inflate(layoutInflater)?.let {
            setContentView(it?.root)
            binding = it
            handleUiClicks()
            getUserList()
        }
    }

    private fun handleUiClicks() {
        binding.btnLoadMore.setOnClickListener { getUserList() }
    }

    private fun getUserList() {
        CoroutineScope(Dispatchers.Main).launch {
            userViewModel.getAllUsers(Utils.PAGEQUERYPARAM, pageToLoad).collect {
                when (it.status) {
                    ApiStatus.LOADING -> { Utils.showProgressDialog(this@MainActivity) }
                    ApiStatus.SUCCESS -> {
                        Utils.dismissprogressDialog()
                        if (allUsers.isEmpty()) { allUsers.addAll(it.data?.users!!); setUpRecyclerView() }
                        else {allUsers.addAll(it.data?.users!!); binding.rclUser.updateRecyclerView() }
                        if (it.data?.page != null && it.data?.total_pages != null)
                            checkIfMoreLoadingIsPossible(it.data?.page, it.data?.total_pages)
                    }
                    ApiStatus.FAILURE -> {
                        Utils.dismissprogressDialog()
                        Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        binding?.rclUser?.apply {
            setUpVertical(this@MainActivity
                , rclAdapter = CommonRecyclerViewAdapter<UserEntity>(this@MainActivity, allUsers, R.layout.item_user) {
                    val itemBinding = it.first
                    val position = it.second
                    val itemSelected = it.third
                    with (itemBinding) {
                        imgItemUser.loadImage(itemSelected?.avatar!!)
                        txtUserName.text = "${itemSelected?.first_name} ${itemSelected?.last_name}"
                        txtUserId.text = "UserId ${itemSelected?.id}"
                        txtUserEmail.text = "${itemSelected?.email}"
                    }
                })
        }
    }

    private fun checkIfMoreLoadingIsPossible(currentPage: Int, totalPages: Int) {
        if (currentPage < totalPages) { binding.btnLoadMore.visible(); ++ pageToLoad }
        else binding.btnLoadMore.gone()
    }
}