package com.michalbrz.fbnotifier.notifications

import android.content.res.Resources
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.*
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.EditText
import android.widget.FrameLayout
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration
import com.michalbrz.fbkeywordnotifier.storage.DummyKeywordStorage
import com.michalbrz.fbnotifier.R
import kotlinx.android.synthetic.main.fragment_notifications.*


class NotificationsFragment : Fragment(), NotificationsView {

    val presenter: NotificationsPresenter = NotificationsPresenter(this, DummyKeywordStorage())
    val keywordsAdapter = KeywordsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity.title = getString(R.string.notifications_fragment)
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        fragmentManager.beginTransaction().replace(R.id.prefsHolder, NotificationsPreferences()).commit()

        keywordsRecyclerView.addItemDecoration(
                SpacingItemDecoration(
                        resources.getDimensionPixelOffset(R.dimen.keyword_horizontal_margin),
                        resources.getDimensionPixelOffset(R.dimen.keyword_vertical_margin)
                ))
        keywordsRecyclerView.layoutManager = createChipsLayoutManager()
        keywordsRecyclerView.adapter = keywordsAdapter

        presenter.init()

        // show() must be called when FAB is fully drawn
        addKeywordButton.viewTreeObserver.addOnGlobalLayoutListener { addKeywordButton?.show() }
        addKeywordButton.setOnClickListener { showAddKeywordDialog() }
    }

    private fun showAddKeywordDialog() {
        val (editText, frameLayout) = createAddKeywordDialogLayout()

        val dialog = AlertDialog.Builder(context)
                .setView(frameLayout)
                .setTitle(R.string.add_keyword)
                .setPositiveButton("OK", { _, _ -> addKeyword(editText.text.toString()) })
                .setNegativeButton("Cancel", { dialog, _ -> dialog.dismiss() })
                .create()
        dialog.show()
        showKeyboard(editText, dialog)

    }

    private fun showKeyboard(editText: EditText, dialog: AlertDialog) {
        editText.requestFocus()
        dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    private fun createAddKeywordDialogLayout(): Pair<EditText, FrameLayout> {
        val editText = EditText(context)
        val layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        layoutParams.setMargins(dpToPx(15), 0, dpToPx(15), 0)
        editText.layoutParams = layoutParams
        val frameLayout = FrameLayout(context)
        frameLayout.addView(editText, layoutParams)
        return Pair(editText, frameLayout)
    }

    fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

    private fun addKeyword(keywordToAdd: String) {
        keywordsAdapter.keywords.add(keywordToAdd)
        keywordsAdapter.notifyDataSetChanged()
    }

    private fun createChipsLayoutManager(): ChipsLayoutManager {
        val chipsLayoutManager = ChipsLayoutManager.newBuilder(context)
                //set vertical gravity for all items in a row. Default = Gravity.CENTER_VERTICAL
                .setChildGravity(Gravity.NO_GRAVITY)
                //whether RecyclerView can scroll. TRUE by default
                .setScrollingEnabled(true)
                //set gravity resolver where you can determine gravity for item in position.
                //This method have priority over previous one
                .setGravityResolver { Gravity.NO_GRAVITY }
                //a layoutOrientation of layout manager, could be VERTICAL OR HORIZONTAL. HORIZONTAL by default
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                // row strategy for views in completed row, could be STRATEGY_DEFAULT, STRATEGY_FILL_VIEW,
                //STRATEGY_FILL_SPACE or STRATEGY_CENTER
                .setRowStrategy(ChipsLayoutManager.STRATEGY_DEFAULT)
                .build()
        return chipsLayoutManager
    }

    override fun showKeywords(keywords: List<String>) {
        keywordsAdapter.keywords.addAll(keywords)
        keywordsAdapter.notifyDataSetChanged()
    }
}

