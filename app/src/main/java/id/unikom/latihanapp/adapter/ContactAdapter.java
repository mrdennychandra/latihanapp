package id.unikom.latihanapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.unikom.latihanapp.FormActivity;
import id.unikom.latihanapp.MainActivity;
import id.unikom.latihanapp.R;
import id.unikom.latihanapp.db.AppDatabase;
import id.unikom.latihanapp.model.Contact;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    Context context;
    List<Contact> contacts;

    public ContactAdapter(Context context, List<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_contact, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Contact contact = contacts.get(i);
        viewHolder.txtNama.setText(contact.nama);
        viewHolder.txtEmail.setText(contact.email);
        viewHolder.txtTgllahir.setText(contact.tgllahir);
        viewHolder.txtTelpon.setText(contact.telpon);
        viewHolder.txtJk.setText(contact.jeniskelamin);
        viewHolder.txtPekerjaan.setText(contact.pekerjaan);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FormActivity.class);
                intent.putExtra("contact",contact);
                context.startActivity(intent);
            }
        });
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Konfirmasi");
                builder.setMessage("Hapus data?");
                builder.setPositiveButton("Hapus",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                delete(contact);
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (contacts != null) ? contacts.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNama, txtEmail, txtTgllahir, txtTelpon, txtJk,txtPekerjaan;
        CardView cardView;
        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNama = (TextView) itemView.findViewById(R.id.txt_nama);
            txtEmail = (TextView) itemView.findViewById(R.id.txt_email);
            txtEmail = (TextView) itemView.findViewById(R.id.txt_email);
            txtTgllahir = (TextView) itemView.findViewById(R.id.txt_tgllahir);
            txtTelpon = (TextView) itemView.findViewById(R.id.txt_telpon);
            txtPekerjaan = (TextView) itemView.findViewById(R.id.txt_pekerjaan);
            txtJk = (TextView) itemView.findViewById(R.id.txt_jk);
            cardView = (CardView) itemView.findViewById(R.id.card);
            btnDelete = (Button) itemView.findViewById(R.id.btn_delete);
        }
    }

    private void delete(Contact contact){
        AppDatabase.getInstance(context).contactDao().delete(contact);
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
