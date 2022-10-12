package com.lcavalle.switfy_companion.views

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lcavalle.switfy_companion.R
import com.lcavalle.switfy_companion.SwiftyCompanion
import com.lcavalle.switfy_companion.dataSources.api42.Cursus
import kotlin.math.roundToInt

class SkillsAdapter : RecyclerView.Adapter<SkillsAdapter.SkillViewHolder>() {
    private var skills: List<Cursus.Companion.Skill> = emptyList()

    class SkillViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nameView: TextView
        var levelView: TextView
        var percentBar: ProgressBar

        init {
            Log.d(SwiftyCompanion.TAG, "init holder")
            nameView = view.findViewById(R.id.textView_skill_name)
            levelView = view.findViewById(R.id.textView_skill_level)
            percentBar = view.findViewById(R.id.progressBar_skill_percent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillViewHolder {
        Log.d(SwiftyCompanion.TAG, "created skills adapter")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.skill_view, parent, false)
        return SkillViewHolder(view)
    }

    override fun onBindViewHolder(holder: SkillViewHolder, position: Int) {
        val skill: Cursus.Companion.Skill = skills[position]
        Log.d(SwiftyCompanion.TAG, "bind ${skill.name}")
        holder.nameView.text = skill.name
        holder.levelView.text = ((skill.level * 100).roundToInt() / 100).toString()
        val totalLevels = skills.map { it.level }.sum()
        holder.percentBar.progress = (skill.level / totalLevels * 100).roundToInt()
    }

    override fun getItemCount(): Int {
        return skills.size
    }

    fun updateSkills(skills: List<Cursus.Companion.Skill>?) {
        Log.d(SwiftyCompanion.TAG, "update adapter skills")
        this.skills = skills ?: emptyList()
        notifyDataSetChanged()
        this.skills.forEach { s: Cursus.Companion.Skill ->
            Log.d(
                SwiftyCompanion.TAG,
                s.toString()
            )
        }
    }
}