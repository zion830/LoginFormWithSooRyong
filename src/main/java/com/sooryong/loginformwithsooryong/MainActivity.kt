package com.sooryong.loginformwithsooryong

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var toX = 0f // 동공 시작점
    var fromX = 0f // 동공 도착점
    var length = 0

    enum class Direction {
        LEFT, CENTER, RIGHT
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        initView()
    }

    fun initView() {
        blinkEyes()

        email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p: Editable?) {
                layout_blink_eye.visibility = View.VISIBLE
                blinkEyes()
            }

            override fun beforeTextChanged(p: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p: CharSequence?, p1: Int, p2: Int, p3: Int) {
                layout_blink_eye.visibility = View.INVISIBLE
                if (p == null) moveWithDirection(Direction.LEFT)
                else if (p.length < 25) moveEyes(p)
            }
        })

        pwd.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) closeEyes() else openEyes()
        }
    }

    // 눈 감기 시작
    fun closeEyes() {
        tv_title.text = "수룡이가 매너있게 눈을 감습니다."
        left_eye.setImageResource(R.drawable.eye3)
        right_eye.setImageResource(R.drawable.eye4)
        left_blink_eye.setImageResource(R.drawable.eye3)
        right_blink_eye.setImageResource(R.drawable.eye4)
        moveWithDirection(Direction.CENTER)
    }

    // 눈 뜨기 시작
    fun openEyes() {
        tv_title.text = "수룡이가 당신을 지켜봅니다."
        left_eye.setImageResource(R.drawable.eye1)
        right_eye.setImageResource(R.drawable.eye2)
        left_blink_eye.setImageResource(R.drawable.eye5)
        right_blink_eye.setImageResource(R.drawable.eye5)

        blinkEyes()
    }

    // 눈 깜빡임 애니메이션 시작
    fun blinkEyes() {
        val anim: Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.blink)
        val anim2: Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.blink2)
        layout_basic_eye.animation = anim
        layout_blink_eye.animation = anim2
    }

    // 방향에 따라 한번에 이동
    fun moveWithDirection(direction: Direction) {
        toX = when (direction) {
            Direction.LEFT -> -15f
            Direction.CENTER -> 0f
            Direction.RIGHT -> 15f
        }

        val eyeAnim = TranslateAnimation(fromX, toX, 0f, 0f)
        eyeAnim.fillAfter = true
        eyeAnim.duration = 400

        layout_basic_eye.startAnimation(eyeAnim)
        layout_blink_eye.startAnimation(eyeAnim)
        fromX = toX

        blinkEyes()
    }

    // 한 글자 단위로 이동
    fun moveEyes(cs: CharSequence?) {
        if (cs != null) {
            // 움직일 거리와 방향 설정
            if (cs.isEmpty() || cs.length == 1) {
                toX = -15f
            } else if (length < cs.length) toX += 1f else toX -= 1f

            val eyeAnim = TranslateAnimation(fromX, toX, 0f, 0f)

            eyeAnim.fillAfter = true
            eyeAnim.duration = 200
            left_eye.startAnimation(eyeAnim)
            right_eye.startAnimation(eyeAnim)
            left_blink_eye.startAnimation(eyeAnim)
            right_blink_eye.startAnimation(eyeAnim)
            length = cs.length
            fromX = toX // 도착점을 다음 애니메이션의 시작점으로
        }
    }
}