package com.example.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helloworld.api.SearchResult
import com.example.helloworld.api.createGitHubApiService
import com.example.helloworld.models.Repo
import com.example.helloworld.repodetails.RepoDetailsActivity
import com.example.helloworld.reposlist.ReposAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var adapter : ReposAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = ReposAdapter { repo ->
            RepoDetailsActivity.startActivity(this, repo)
        }

        val list: RecyclerView = findViewById(R.id.list)
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter

        val service = createGitHubApiService()
        service.searchRepositories("android").enqueue(object : Callback<SearchResult> {
            override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                val repos = response.body()?.items.orEmpty()
                adapter.submitList(repos)
            }

            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                //handle failure
            }

        })




    }
}
