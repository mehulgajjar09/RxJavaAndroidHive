package com.autotradetech.rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_disposible.*

class DisposableActivity : AppCompatActivity() {

    var log: String = ""
    lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disposible)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = intent.getStringExtra("title")

        setUpBasicSteps()
    }

    private fun setUpBasicSteps() {
        val movieListObservable = Observable.just("Avangers", "Batman", "Captain America", "Death Race")
        val movieListObserver = getMovieListObserver()

        movieListObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(movieListObserver)

    }

    private fun getMovieListObserver(): Observer<String> {
        return object : Observer<String> {
            override fun onComplete() {
                txtDisposableLog.text = log + "\nonComplete()"
            }

            override fun onSubscribe(d: Disposable) {
                log = "onSubscribe()\n\n"
                disposable = d
            }

            override fun onNext(t: String) {
                log = log + t + "\n"
            }

            override fun onError(e: Throwable) {
                log = "\nonError()" + e.localizedMessage
            }

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}
