package com.mungeun.finedust.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mungeun.finedust.data.model.monitoringstation.MonitoringStation
import com.mungeun.finedust.data.model.airquality.Item
import com.mungeun.finedust.data.repository.TmCoordinatesRepository
import com.mungeun.finedust.data.repository.MonitoringStationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class MainVeiwModel @Inject constructor(
    private val tmCoordinatesRepository: TmCoordinatesRepository,
    private val monitoringStationRepository: MonitoringStationRepository,
) : ViewModel() {


    private var _fetchState = MutableLiveData<FetchState>()
    val fetchState : LiveData<FetchState>
        get() = _fetchState

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isrefresh = MutableLiveData<Boolean>(true)
    val isrefresh: LiveData<Boolean> get() = _isrefresh

    fun showProgress() {
        _isLoading.value = true
    }

    fun hideProgress() {
        _isLoading.value = false
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()

        when (throwable) {
            is SocketException -> _fetchState.postValue(FetchState.BAD_INTERNET)
            is HttpException -> _fetchState.postValue(FetchState.PARSE_ERROR)
            is UnknownHostException -> _fetchState.postValue(FetchState.WRONG_CONNECTION)
            is ConnectException -> _fetchState.postValue(FetchState.CONNECT_ERROR)
            else -> _fetchState.postValue(FetchState.FAIL)
        }
    }


    private val _monitoringStation: MutableLiveData<MonitoringStation> =
        MutableLiveData<MonitoringStation>()
    val monitoringStation: LiveData<MonitoringStation> = _monitoringStation

    private val _items : MutableLiveData<Item> = MutableLiveData<Item>()
    val items : LiveData<Item> = _items

    fun getTmCoordinates(longtitude: Double, latitude: Double) {
        viewModelScope.launch(exceptionHandler) {
            showProgress()
            val tmCoordinates = tmCoordinatesRepository.getTmCoordinates(longtitude, latitude)
            _monitoringStation.value = monitoringStationRepository.getMonitoringStation(tmCoordinates.x, tmCoordinates.y)
            _items.value = monitoringStationRepository.getItems(_monitoringStation.value!!.stationName!!)
            hideProgress()
            _isrefresh.value = false


        }

    }

    companion object{
        enum class FetchState{
            BAD_INTERNET,
            PARSE_ERROR,
            CONNECT_ERROR,
            WRONG_CONNECTION,
            FAIL
        }
    }


}