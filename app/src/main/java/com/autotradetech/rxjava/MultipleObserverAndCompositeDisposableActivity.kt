package com.autotradetech.rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.functions.Predicate
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_multiple_observer_and_composite_disposable.*

class MultipleObserverAndCompositeDisposableActivity : AppCompatActivity() {

    var logFirst: String = ""
    var logSecond: String = ""
    lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiple_observer_and_composite_disposable)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = intent.getStringExtra("title")

        compositeDisposable = CompositeDisposable()

        setUpBasicSteps()
    }

    private fun setUpBasicSteps() {
        val movieListObservable = Observable.just("Avangers", "Batman", "Captain America", "Death Race")
        val movieLisContainAtObserver = getMovieListObserverContainA()
        val movieLisAllCapitaltObserver = getMovieListObserverAllCapital()

        compositeDisposable.add(
            movieListObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(object : Predicate<String> {
                    override fun test(t: String): Boolean {
                        return t.contains("A")
                    }
                })
                .map(object : Function<String, String> {
                    override fun apply(t: String): String {
                        return t.toUpperCase()
                    }
                })
                .subscribeWith(movieLisContainAtObserver)
        )

        compositeDisposable.add(
            movieListObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(object : Predicate<String> {
                    override fun test(t: String): Boolean {
                        return t.contains("A")
                    }
                })
                .subscribeWith(movieLisAllCapitaltObserver)
        )

    }

    private fun getMovieListObserverContainA(): DisposableObserver<String> {
        return object : DisposableObserver<String>() {
            override fun onComplete() {
                txtFirstDisposable.text = logFirst + "\nonComplete()"
            }

            override fun onNext(t: String) {
                logFirst = logFirst + t + "\n"
            }

            override fun onError(e: Throwable) {
                logFirst = "\nonError()" + e.localizedMessage
            }

        }
    }

    private fun getMovieListObserverAllCapital(): DisposableObserver<String> {
        return object : DisposableObserver<String>() {
            override fun onComplete() {
                txtSecondDisposable.text = logSecond + "\nonComplete()"
            }

            override fun onNext(t: String) {
                logSecond = logSecond + t + "\n"
            }

            override fun onError(e: Throwable) {
                logSecond = "\nonError()" + e.localizedMessage
            }

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
