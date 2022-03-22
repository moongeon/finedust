package com.mungeun.finedust

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import com.mungeun.finedust.databinding.ActivityMainBinding
import com.mungeun.finedust.viewmodels.MainVeiwModel
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainVeiwModel by viewModels()
    private lateinit var binding: ActivityMainBinding


    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var cancellationTokenSource: CancellationTokenSource? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        with(binding) {
            // init
            vm = viewModel
            lifecycleOwner = this@MainActivity

            // listener
            binding.refresh.setOnRefreshListener {
                fetchLocation()

            }
        }

        with(viewModel){
            isLoading.observe(this@MainActivity,{ it ->
                if(it == false) binding.refresh.isRefreshing = false
            })

            items.observe(this@MainActivity,{ it ->
              if(it != null) {
                  binding.contentsLayout.animate()
                      .alpha(1F)
                      .start()
                  binding.errorDescriptionTextView.visibility = View.GONE
              }else{
                  binding.contentsLayout.alpha = 0F

              }
            })

            fetchState.observe(this@MainActivity,{ it ->
                when(it){
                    MainVeiwModel.Companion.FetchState.BAD_INTERNET -> binding.errorDescriptionTextView.text = "인터넷 연결을 확인해 주세요."
                    MainVeiwModel.Companion.FetchState.CONNECT_ERROR -> binding.errorDescriptionTextView.text = "인터넷 연결을 확인해 주세요."
                    MainVeiwModel.Companion.FetchState.FAIL -> binding.errorDescriptionTextView.text = "인터넷 연결을 확인해 주세요."
                    MainVeiwModel.Companion.FetchState.PARSE_ERROR -> binding.errorDescriptionTextView.text = "인터넷 연결을 확인해 주세요."
                    MainVeiwModel.Companion.FetchState.WRONG_CONNECTION -> binding.errorDescriptionTextView.text = "인터넷 연결을 확인해 주세요."
                }
                binding.errorDescriptionTextView.visibility = View.VISIBLE
                binding.refresh.isRefreshing = false
                binding.contentsLayout.alpha = 0F

            })

        }





        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        enableMyLocation()
        fetchLocation()

    }

    fun enableMyLocation() {
        val context = this ?: return
        if (REQUIRED_PERMISSIONS.all { permission ->
                ContextCompat.checkSelfPermission(context,
                    permission) == PackageManager.PERMISSION_GRANTED
            }) {
            Toast.makeText(this, "권한 승인", Toast.LENGTH_SHORT).show()
        } else {
            requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)

        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach { permission ->
                when {
                    permission.value -> {
                        Toast.makeText(this, "권한 승인", Toast.LENGTH_SHORT).show()
                    }
                    shouldShowRequestPermissionRationale(permission.key) -> {
                        Toast.makeText(this, "앱 기동을 위해서는 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    else -> {
                        Toast.makeText(this, "권한 거절", Toast.LENGTH_SHORT).show()
                    }

                }


            }
        }

    private fun fetchLocation() {
        cancellationTokenSource = CancellationTokenSource()

        fusedLocationProviderClient
            .getCurrentLocation(
                LocationRequest.PRIORITY_HIGH_ACCURACY,
                cancellationTokenSource!!.token
            ).addOnSuccessListener { location ->
                viewModel.getTmCoordinates(location.longitude, location.latitude)

            }
    }


    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

    }

}