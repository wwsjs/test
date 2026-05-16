import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '项目编号',
    align:"center",
    dataIndex: 'projectNo'
   },
   {
    title: '项目名称',
    align:"center",
    dataIndex: 'projectName'
   },
   {
    title: '开始时间',
    align:"center",
    dataIndex: 'startDate',
    customRender:({text}) =>{
      text = !text ? "" : (text.length > 10 ? text.substr(0,10) : text);
      return text;
    },
   },
   {
    title: '结束时间',
    align:"center",
    dataIndex: 'endDate',
    customRender:({text}) =>{
      text = !text ? "" : (text.length > 10 ? text.substr(0,10) : text);
      return text;
    },
   },
   {
    title: '项目负责人ID',
    align:"center",
    dataIndex: 'leaderId_dictText',
    customRender: ({ text, record }) => text || record?.leaderId || '-'
   },
   {
    title: '项目成员ID',
    align:"center",
    dataIndex: 'memberId_dictText',
    customRender: ({ text, record }) => text || record?.memberId || '-'
   },
   {
    title: '合同经费',
    align:"center",
    dataIndex: 'contractAmount'
   },
   {
    title: '所属组',
    align:"center",
    dataIndex: 'groupId_dictText'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '项目编号',
    field: 'projectNo',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入项目编号!'},
          ];
     },
  },
  {
    label: '项目名称',
    field: 'projectName',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入项目名称!'},
          ];
     },
  },
  {
    label: '开始时间',
    field: 'startDate',
    component: 'DatePicker',
    componentProps: {
      valueFormat: 'YYYY-MM-DD'
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入开始时间!'},
          ];
     },
  },
  {
    label: '结束时间',
    field: 'endDate',
    component: 'DatePicker',
    componentProps: {
      valueFormat: 'YYYY-MM-DD'
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入结束时间!'},
          ];
     },
  },
  {
    label: '项目负责人ID',
    field: 'leaderId',
    component: 'JSearchSelect',
    componentProps:{
       dict:"lab_user,username,id"
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入项目负责人ID!'},
          ];
     },
  },
  {
    label: '项目成员ID',
    field: 'memberId',
    component: 'JSelectMultiple',
    componentProps:{
        dictCode:"lab_user,username,id"
     },
  },
  {
    label: '合同经费',
    field: 'contractAmount',
    component: 'Input',
  },
  {
    label: '所属组',
    field: 'groupId',
    component: 'JSearchSelect',
    componentProps:{
       dict:"lab_group,mentor,group_id"
    },
  },
	// TODO 主键隐藏字段，目前写死为ID
	{
	  label: '',
	  field: 'id',
	  component: 'Input',
	  show: false
	},
];

// 高级查询数据
export const superQuerySchema = {
  projectNo: {title: '项目编号',order: 0,view: 'text', type: 'string',},
  projectName: {title: '项目名称',order: 1,view: 'text', type: 'string',},
  startDate: {title: '开始时间',order: 2,view: 'date', type: 'string',},
  endDate: {title: '结束时间',order: 3,view: 'date', type: 'string',},
  leaderId: {title: '项目负责人ID',order: 4,view: 'sel_search', type: 'string',dictTable: "lab_user", dictCode: 'id', dictText: 'username',},
  memberId: {title: '项目成员ID',order: 5,view: 'list_multi', type: 'string',dictTable: "lab_user", dictCode: 'id', dictText: 'username',},
  contractAmount: {title: '合同经费',order: 6,view: 'text', type: 'string',},
  groupId: {title: '所属组',order: 7,view: 'sel_search', type: 'string',dictTable: "lab_group", dictCode: 'group_id', dictText: 'mentor',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
