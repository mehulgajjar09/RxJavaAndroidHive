package com.autotradetech.rxjava

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_basic_steps.*

class BasicStepsActivity : AppCompatActivity() {

    var log: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_steps)

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
                txtBasicStepsLog.text = log + "\nonComplete()"
            }

            override fun onSubscribe(d: Disposable) {
                log = "onSubscribe()\n\n"
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
}
