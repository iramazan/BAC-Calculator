package csc420.baccalculator;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.TreeMap;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactHolder> {

    private TreeMap<String, String> contacts = new TreeMap<>();

    public static class ContactHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView number;

        public ContactHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.contact_name);
            number = itemView.findViewById(R.id.contact_number);
        }
    }

    public ContactsAdapter() {
        // TODO: Load actual dataset
        contacts.put("John Doe", "1234567");
        contacts.put("Jane Doe", "7202710");
        contacts.put("Will Smith", "0165882");
    }

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_contact, viewGroup, false);
        return new ContactHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder contactHolder, int i) {
        String key = (String) contacts.keySet().toArray()[i];
        contactHolder.name.setText(key);
        contactHolder.number.setText(contacts.get(key));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
