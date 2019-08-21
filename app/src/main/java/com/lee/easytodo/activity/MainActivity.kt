package com.lee.easytodo.activity

import android.content.ClipData
import android.content.Context
import android.inputmethodservice.ExtractEditText
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.lee.easytodo.R
import com.lee.easytodo.activity.base.BaseActivity
import com.lee.easytodo.fragment.IdeaPoolFragment
import com.lee.easytodo.fragment.ScheduleFragment
import com.lee.easytodo.fragment.base.BaseFragment
import com.lee.easytodo.presenter.MainActivityPresenter
import com.lee.easytodo.util.logD
import com.lee.easytodo.view.InteractDragEventViewPager
import com.lee.easytodo.view.calendarview.OnDraggedAboveListener


/**
 * MainActivity of things
 */
class MainActivity : BaseActivity(), View.OnClickListener, View.OnLongClickListener,
    BaseFragment.OnFragmentInteractionListener {

    override fun onFragmentInteraction(uri: Uri) {
        //TODO
    }

    private val fragmentModels = ArrayList<FragmentModel>(2)
    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var viewPagerContent: InteractDragEventViewPager
    private lateinit var pagerAdapter: FragmentPagerAdapter
    private lateinit var tabLayout: TabLayout

    //TODO TEST
    private lateinit var buttonToDrag: Button

    private var textInputView: View? = null
    private var textViewHolder: TextInputViewHolder? = null
    private var voiceInputView: View? = null
    private var voiceViewHolder: VoiceInputViewHolder? = null

    private lateinit var mainActivityPresenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_layout)

        mainActivityPresenter = MainActivityPresenter(this)
        mainActivityPresenter.load()
    }

    fun initView() {
        tabLayout = findViewById(R.id.tabLayout)
        floatingActionButton = findViewById(R.id.floatingActionButton)
        viewPagerContent = findViewById(R.id.viewPagerContent)
        fragmentModels.add(
            0,
            FragmentModel(ScheduleFragment(), R.string.main_activity_schedule_fragment_title)
        )
        fragmentModels.add(
            1,
            FragmentModel(IdeaPoolFragment(), R.string.main_activity_idea_pool_fragment_title)
        )

        buttonToDrag = findViewById(R.id.buttonToDrag)
    }

    fun initAction() {
        floatingActionButton.setOnClickListener(this)
        floatingActionButton.setOnLongClickListener(this)

        viewPagerContent.onDraggedEdgeListener = object : OnDraggedAboveListener {
            override fun onDraggedEdgeLong(direction: OnDraggedAboveListener.Direction) {
                mainActivityPresenter.onScroll(direction)
            }
        }
        pagerAdapter = object :
            FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            override fun getItem(position: Int): Fragment {
                if (position >= 0 && position < fragmentModels.size) {
                    return fragmentModels[position].fragment
                } else {
                    if (position < 0) {
                        return fragmentModels[0].fragment
                    } else {
                        return fragmentModels[fragmentModels.size - 1].fragment
                    }
                }
            }

            override fun getCount(): Int {
                return fragmentModels.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return getString(fragmentModels[position].string)
            }

        }
        viewPagerContent.adapter = pagerAdapter

        tabLayout.setupWithViewPager(viewPagerContent)
        viewPagerContent.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                logD("page selected $position")
            }
        })

        buttonToDrag.setOnLongClickListener(this)
    }

    fun scrollLeft() {
        if (viewPagerContent.currentItem > 0) {
            viewPagerContent.currentItem -= 1
        }
    }

    fun scrollRight() {
        if (viewPagerContent.currentItem < fragmentModels.size - 1) {
            viewPagerContent.currentItem += 1
        }
    }

    fun initTextInputView() {
        textInputView =
            LayoutInflater.from(this)
                .inflate(R.layout.layout_create_material_by_text_view, null)
        textViewHolder = TextInputViewHolder(textInputView!!)
    }

    fun initTextInputAction() {

        textViewHolder?.editTextMaterial?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                var newText = s.toString()
                logD("input text changed: $newText")
                if (TextUtils.isEmpty(newText)) {
                    textViewHolder?.buttonSendMaterial?.visibility = View.GONE
                } else {
                    textViewHolder?.buttonSendMaterial?.visibility = View.VISIBLE
                }
            }

        })
        textViewHolder?.buttonSendMaterial?.setOnClickListener(this)
    }

    fun initVoiceInputView() {
        voiceInputView =
            LayoutInflater.from(this)
                .inflate(R.layout.layout_create_material_by_voice_view, null)
        voiceViewHolder = VoiceInputViewHolder(voiceInputView!!)
    }

    fun initVoiceInputAction() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.floatingActionButton -> mainActivityPresenter.onCreateMaterialByText()
            R.id.buttonSendMaterial -> mainActivityPresenter.onCreatedMaterialByText()
            else -> {
                //do Nothing
            }
        }
    }

    override fun onLongClick(v: View?): Boolean {
        when (v?.id) {
            R.id.floatingActionButton -> {
                mainActivityPresenter.onCreateMaterialByVoice()
                //TODO test
                mainActivityPresenter.sendEmptyMessageDelay(
                    MainActivityPresenter.MESSAGE_TYPE_END_VOICE_INPUT,
                    3000
                )
                return true
            }

            R.id.buttonToDrag -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v?.startDragAndDrop(
                        ClipData(
                            "button",
                            arrayOf("from button"),
                            ClipData.Item("item")),
                        View.DragShadowBuilder(v), v, 0)
                } else {
                    v?.startDrag(
                        ClipData("button", arrayOf("from button"), ClipData.Item("item")),
                        View.DragShadowBuilder(v),
                        v,
                        0
                    )
                }
                return true
            }
            else -> return false
        }
    }

    fun showTextInputUI() {
        if (isShowingTextInputView()) return

        if (textInputView == null) {
            mainActivityPresenter.loadTextInputView()
        }

        if (textInputView?.visibility != View.VISIBLE) textInputView?.visibility = View.VISIBLE
        showForegroundView(textInputView!!)
        //getfocus

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(textViewHolder?.editTextMaterial, InputMethodManager.SHOW_IMPLICIT)
    }

    fun dismissTextInputUI() {
        if (textInputView != null && textInputView?.visibility != View.GONE) {
            textInputView?.visibility = View.GONE
            dismissForegroundView(textInputView!!)
        }
    }

    fun showVoiceInputUI() {
        if (isShowingVoiceInputView()) return

        if (voiceInputView == null) {
            mainActivityPresenter.loadVoiceInputView()
        }
        if (voiceInputView?.visibility != View.VISIBLE) voiceInputView?.visibility = View.VISIBLE
        showForegroundView(voiceInputView!!)
    }

    fun dismissVoiceInputUI() {
        if (voiceInputView != null && voiceInputView?.visibility != View.GONE) {
            voiceInputView?.visibility = View.GONE
            dismissForegroundView(voiceInputView!!)
        }
    }

    fun popMaterialText(): String {
        if (textViewHolder != null) {
            var materialText = textViewHolder?.editTextMaterial?.text.toString()
            textViewHolder?.editTextMaterial?.setText("")
            return materialText
        } else {
            return ""
        }
    }

    override fun onBackPressed() {
        if (mainActivityPresenter.cancel()) {
            //do nothing
        } else {
            super.onBackPressed()
        }
    }

    fun popMaterialVoice(): String {
        //TODO voice action
        return "voicing..."
    }

    override fun onDestroy() {
        textInputView = null
        voiceInputView = null
        mainActivityPresenter.onDestroy()
        super.onDestroy()
    }

    fun isShowingInputView(): Boolean {
        return isShowingTextInputView() || isShowingVoiceInputView()
    }

    fun isShowingTextInputView(): Boolean {
        return textInputView != null && textInputView?.visibility == View.VISIBLE
    }

    fun isShowingVoiceInputView(): Boolean {
        return voiceInputView != null && voiceInputView?.visibility == View.VISIBLE
    }

    companion object {

        class FragmentModel(fragment: BaseFragment, @StringRes string: Int) {
            val fragment = fragment
            val string = string
        }

        class TextInputViewHolder {
            val editTextMaterial: ExtractEditText
            val buttonSendMaterial: Button

            constructor(root: View) {
                editTextMaterial = root.findViewById(R.id.editTextCreateMaterial)
                buttonSendMaterial = root.findViewById(R.id.buttonSendMaterial)
            }
        }

        class VoiceInputViewHolder {
            val textViewContent: TextView

            constructor(root: View) {
                this.textViewContent = root.findViewById(R.id.textViewContent)
            }
        }
    }
}