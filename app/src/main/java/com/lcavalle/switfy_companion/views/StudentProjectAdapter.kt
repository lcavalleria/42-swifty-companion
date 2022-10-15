package com.lcavalle.switfy_companion.views

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lcavalle.switfy_companion.R
import com.lcavalle.switfy_companion.SwiftyCompanion
import com.lcavalle.switfy_companion.dataSources.api42.StudentProject

class StudentProjectAdapter :
    RecyclerView.Adapter<StudentProjectAdapter.StudentProjectViewHolder>() {
    private var projects: List<StudentProject> = emptyList()

    class StudentProjectViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nameView: TextView
        var scoreView: TextView
        var validatedBackground: View

        init {
            nameView = view.findViewById(R.id.textView_project_name)
            scoreView = view.findViewById(R.id.textView_project_score)
            validatedBackground = view.findViewById(R.id.view_project_validated)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentProjectViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_project_view, parent, false)
        return StudentProjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentProjectViewHolder, position: Int) {
        val project: StudentProject = projects[position]
        holder.nameView.text = project.name
        holder.scoreView.text = project.finalMark.toString()
        holder.validatedBackground.setBackgroundResource(
            if (project.validated == true) R.drawable.project_validated_background
            else R.drawable.project_failed_background
        )
    }

    override fun getItemCount(): Int {
        return projects.size
    }

    fun updateProjects(projects: List<StudentProject>?) {
        Log.d(SwiftyCompanion.TAG, "update adapter projects")
        this.projects = projects ?: emptyList()
        notifyDataSetChanged()
    }
}