package com.example.rafae.booktracker

import com.example.rafae.booktracker.models.Note

/**
 * Created by rafae on 24/09/2017.
 */

/*
 * Aggregates all communication operations between MVP pattern layer:
 * Model, View and Presenter
 */
interface MainMVP {

    /**
     * View mandatory methods. Available to Presenter
     * Presenter -> View
     */
    interface RequiredViewOps {
        fun showToast(msg: String)

        fun showAlert(msg: String)
        // any other ops
    }

    /**
     * Operations offered from Presenter to View
     * View -> Presenter
     */
    interface PresenterOps {
        fun onConfigurationChanged(view: RequiredViewOps)

        fun onDestroy(isChangingConfig: Boolean)

        fun newNote(textoNota: String)

        fun removeNote(nota: Note)
        // any other ops to be called from View
    }

    /**
     * Operations offered from Presenter to Model
     * Model -> Presenter
     */
    interface RequiredPresenterOps {
        fun onNoteInserted(novaNota: Note)

        fun onNoteRemoved(notaRemovida: Note)

        fun onError(errorMsg: String)
        // Any other returning operation Model -> Presenter
    }

    /**
     * Model operations offered to Presenter
     * Presenter -> Model
     */
    interface ModelOps {
        fun insertNote(nota: Note)

        fun removeNote(nota: Note)

        fun onDestroy()
        // Any other data operation
    }
}