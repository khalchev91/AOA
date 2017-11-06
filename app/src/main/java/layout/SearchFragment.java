package layout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.khalincheverria.mydictionary.Model.Contact;
import com.khalincheverria.mydictionary.R;


@SuppressWarnings("ConstantConditions")
public class SearchFragment extends Fragment {


    public SearchFragment() {

    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_search, container, false);
        boolean isTree;
        Bundle bundle=getArguments();
        isTree=bundle.getBoolean("Tree");

        TextView nameOfWord=(TextView)view.findViewById(R.id.name_of_word);
        TextView definition=(TextView)view.findViewById(R.id.def);
        TextView partOfSpeech=(TextView)view.findViewById(R.id.pos);

        TextView position=(TextView)view.findViewById(R.id.position_found);
        TextView timeTaken=(TextView)view.findViewById(R.id.time_taken);
        Contact contact;
        if(isTree){
            contact =(Contact)bundle.getSerializable("Contacts");
            double time=bundle.getDouble("time");
            int count=bundle.getInt("position");

            nameOfWord.setText(contact.getWord());
            partOfSpeech.setText(contact.getPartOfSpeech());
            definition.setText(contact.getDefinition());

            System.out.println(time);

            timeTaken.setText(String.format("%.6f seconds",time));

            position.setText(Integer.toString(count));

        }else {
            contact =(Contact) bundle.getSerializable("Contacts");
            double time=bundle.getDouble("time");
            int count=bundle.getInt("position");

            nameOfWord.setText(contact.getWord());
            partOfSpeech.setText(contact.getPartOfSpeech());
            definition.setText(contact.getDefinition());

            timeTaken.setText(String.format("%.6f seconds",time));

            position.setText(Integer.toString(count));
        }
        return view;
    }

}
