package com.saefulrdevs.articleflow.view

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.core.text.HtmlCompat
import com.saefulrdevs.articleflow.R
import com.saefulrdevs.articleflow.data.ProgrammingLanguage
import com.saefulrdevs.articleflow.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var programmingLanguage: ProgrammingLanguage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val isLightTheme = when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> false
            Configuration.UI_MODE_NIGHT_NO -> true
            else -> false
        }

        setStatusBarIconColor(this, isLightTheme)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Detail"

        programmingLanguage = intent.getParcelableExtra("DATA") ?: ProgrammingLanguage("", "", 0, 0, "", "")

        setupViews()

        binding.actionShare.setOnClickListener(shareDetail())
    }

    private fun setStatusBarIconColor(activity: Activity, isLightTheme: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = activity.window.decorView
            if (isLightTheme) {
                decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                decor.systemUiVisibility = 0
            }
        }
    }

    private fun shareDetail(): View.OnClickListener {
        return View.OnClickListener {
            val name = binding.tvTitle.text.toString()
            val description = binding.tvDescription.text.toString()
            val release = binding.tvRelease.text.toString()
            val inventor = binding.tvInventor.text.toString()

            val data = "$name\n$inventor\n$release\n$description"

            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, data)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, "Bagikan ke"))
        }
    }

    private fun setupViews() {
        binding.tvTitle.text = programmingLanguage.name
        binding.imageView.setImageResource(programmingLanguage.photo)
        binding.tvDescription.text = programmingLanguage.description
        binding.tvRelease.text = getString(R.string.release_year, programmingLanguage.release)
        binding.tvInventor.text = getString(R.string.inventor, programmingLanguage.inventor)

        binding.tvLink.text = HtmlCompat.fromHtml(getString(R.string.website_link, programmingLanguage.link), HtmlCompat.FROM_HTML_MODE_COMPACT)
        binding.tvLink.movementMethod = LinkMovementMethod.getInstance()
        binding.tvLink.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(programmingLanguage.link))
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}