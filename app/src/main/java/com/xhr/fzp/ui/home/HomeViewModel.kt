package com.xhr.fzp.ui.home

import androidx.lifecycle.ViewModel
import com.xhr.fzp.ui.case.CaseFragment
import com.xhr.fzp.ui.knowledge.KnowledgeFragment
import com.xhr.fzp.ui.mime.MimeFragment

class HomeViewModel : ViewModel() {

    val fragments = arrayOf(KnowledgeFragment(), CaseFragment(), MimeFragment())

//    val knowledgeFragment = KnowledgeFragment()
//    val caseFragment = CaseFragment()
//    val mimeFragment = MimeFragment()

//    val studyFragment = StudyFragment()
//    val caseFragment = CaseFragment()
//    val mimeFragment = MimeFragment()

}