package com.autotradetech.rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.autotradetech.rxjava.Model.Notes
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_custom_data_type_operator.*

class CustomDataTypeOperatorActivity : AppCompatActivity() {

    var notesList: ArrayList<Notes> = ArrayList()
    lateinit var compositeDisposable: CompositeDisposable
    var log: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_data_type_operator)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = intent.getStringExtra("title")

        compositeDisposable = CompositeDisposable()
        setNotesArray()
        setBasicSteps()
    }

    private fun setBasicSteps() {
        val notesObservable = Observable.create(object : ObservableOnSubscribe<Notes> {
            override fun subscribe(emitter: ObservableEmitter<Notes>) {
                for (note in notesList) {
                    if (!emitter.isDisposed) {
                        emitter.onNext(note)
                    }
                }

                if (!emitter.isDisposed) {
                    emitter.onComplete()
                }
            }
        })

        val notesObserver = getNoteObserver()

        compositeDisposable.add(
            notesObservable
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .map(object : Function<Notes,Notes>{
                    override fun apply(t: Notes): Notes {
                        t.note = t.note.toUpperCase()
                        return t
                    }
                })
                .subscribeWith(notesObserver)
        )
    }

    private fun getNoteObserver(): DisposableObserver<Notes> {
        return object : DisposableObserver<Notes>() {
            override fun onNext(t: Notes) {
                log = log + t.note + "\n"
            }

            override fun onComplete() {
                runOnUiThread{
                    txtCustomDataTypeOperatorLog.text = log + "\nonComplete()"
                }
            }

            override fun onError(e: Throwable) {
                log = "\nonError()" + e.localizedMessage
            }

        }
    }

    private fun setNotesArray() {
        notesList.add(Notes(1, "buy tooth paste!"))
        notesList.add(Notes(1, "call brother!"))
        notesList.add(Notes(1, "watch narcos tonight!"))
        notesList.add(Notes(1, "pay power bill!"))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
