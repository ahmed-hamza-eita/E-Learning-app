package com.hamza.e_learningapp.ui.student.solve_quiz;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.hamza.e_learningapp.data.MySharedPrefrance;
import com.hamza.e_learningapp.models.ModelQuiz;
import com.hamza.e_learningapp.models.ModelUserAnswer;
import com.hamza.e_learningapp.utils.Const;
import com.hamza.e_learningapp.utils.Helper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SolveQuizViewModel extends ViewModel {

    private DatabaseReference ref;
    private FirebaseAuth auth;
    private ArrayList<String> allQuizList = new ArrayList<>();
    MutableLiveData<List<String>> allQuizLiveData = new MutableLiveData<>();
    MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    MutableLiveData<Boolean> checkIfUserAnsweredLiveData = new MutableLiveData<>();

    private ArrayList<ModelQuiz> getQuizList = new ArrayList<>();
    MutableLiveData<ArrayList<ModelQuiz>> getQuizLiveData = new MutableLiveData<>();

    @Inject
    public SolveQuizViewModel(DatabaseReference reference, FirebaseAuth auth) {
        this.auth = auth;
        this.ref = reference;
    }

    public void getAllQuizzes(String courseId) {
        ref.child(Const.REF_QUIZ).child(courseId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allQuizList.clear();
                ref.getKey();
                if (snapshot.getValue() != null) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        allQuizList.add(ds.getKey());
                    }
                    allQuizLiveData.setValue(allQuizList);
                } else {
                    errorLiveData.setValue("No quizzes found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorLiveData.setValue(error.getMessage());
            }
        });
    }

    public void uploadResult(String courseId, String quizId, int grade) {
        ref.child(Const.REF_QUIZ_ANSWER).child(courseId).child(quizId).child(auth.getUid())
                .setValue(new ModelUserAnswer(MySharedPrefrance.getUserName()
                        , MySharedPrefrance.getUserEmail(), MySharedPrefrance.getUserId(), grade));

        uploadDegree(courseId, grade);

    }

    public void uploadDegree(String courseId, double grade) {
        ref.child(Const.REF_COURSES).child(courseId).child(Const.REF_COURSE_MEMBERS).
                child(Helper.removeDot(MySharedPrefrance.getUserEmail())).child("quizGrade")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot == null) {
                            return;
                        } else {
                            double oldGrade = snapshot.getValue(Double.class);
                            ref.child(Const.REF_COURSES).child(courseId).child(Const.REF_COURSE_MEMBERS).
                                    child(Helper.removeDot(MySharedPrefrance.getUserEmail())).child("quizGrade")
                                    .setValue(oldGrade + grade);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        errorLiveData.setValue(error.getMessage());
                    }
                });
    }

    public void checkIfUserAnswered(String courseId, String quizID) {
        ref.child(Const.REF_QUIZ_ANSWER).child(courseId).child(quizID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                checkIfUserAnsweredLiveData.setValue(snapshot.hasChild(auth.getUid()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorLiveData.setValue(error.getMessage());
            }
        });
    }

    public void getQuiz(String courseId, String quizId) {
        ref.child(Const.REF_QUIZ).child(courseId).child(quizId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getQuizList.clear();
                if (snapshot != null) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        getQuizList.add(ds.getValue(ModelQuiz.class));
                    }
                    getQuizLiveData.setValue(getQuizList);
                } else {
                    errorLiveData.setValue("No quiz found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorLiveData.setValue(error.getMessage());
            }
        });
    }
}
